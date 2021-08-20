package id.meier.timetracking.adapter.out.persistence;

import id.meier.timetracking.adapter.out.persistence.entities.AssignmentEntity;
import id.meier.timetracking.adapter.out.persistence.entities.AssignmentRefHitParadeProjectPhaseTaskEntity;
import id.meier.timetracking.adapter.out.persistence.mapper.*;
import id.meier.timetracking.adapter.out.persistence.repositories.AssignmentRepository2;
import id.meier.timetracking.adapter.out.persistence.repositories.AssignmentRepositorySelection;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentCommand;
import id.meier.timetracking.application.port.out.ManageAssignmentPort;
import id.meier.timetracking.application.port.out.SelectionAssignmentPort;
import id.meier.timetracking.application.port.out.commands.*;
import id.meier.timetracking.domain.Assignment;
import id.meier.timetracking.domain.AssignmentRefHitParadeProjectPhaseTask;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssignmentPersistenceAdapter implements ManageAssignmentPort, SelectionAssignmentPort {
    private AssignmentEntityMapper assignmentMapper = new AssignmentEntityMapper();
    private ProjectEntityMapper projectMapper = new ProjectEntityMapper();
    private PhaseEntityMapper phaseMapper = new PhaseEntityMapper();
    private TaskEntityMapper taskMapper = new TaskEntityMapper();
    private AssignmentRefHitParadeProjectPhaseTaskEntityMapper assignmentRefHitParadeProjectPhaseTaskEntityMapper
            = new AssignmentRefHitParadeProjectPhaseTaskEntityMapper();
    private AssignmentRepositorySelection selectAssignmentRepository;
    private AssignmentRepository2 repository;
    private ProjectStructurePersistenceAdapter projectStructurePersistenceAdapter;

    public AssignmentPersistenceAdapter(AssignmentRepository2 repository,
                                        @Qualifier("assignmentRepositorySelectionImpl") AssignmentRepositorySelection selectAssignmentRepository,
                                        ProjectStructurePersistenceAdapter projectStructurePersistenceAdapter
    ) {
        this.selectAssignmentRepository = selectAssignmentRepository;
        this.repository = repository;
        this.projectStructurePersistenceAdapter = projectStructurePersistenceAdapter;
    }

    @Override
    public Assignment saveAssignment(SaveAssignmentEntityCommand assignmentCommand) {
        ensureDependingEntityArePersisted(assignmentCommand.getAssignment());
        AssignmentEntity assignment = assignmentMapper.toEntityObject(assignmentCommand.getAssignment());
        assignment = save(assignment);
        assignmentMapper.toDomainObject(assignment, assignmentCommand.getAssignment());
        return assignmentCommand.getAssignment();
    }

    @Override
    public void removeAssignment(RemoveAssignmentEntityCommand assignmentCommand) {
        AssignmentEntity assignment = assignmentMapper.toEntityObject(assignmentCommand.getAssignment());
        delete(assignment);
    }

    private AssignmentEntity save(AssignmentEntity element) {
        return repository.save(element);
    }

    private void ensureDependingEntityArePersisted(Assignment element) {
        if (element.getTask() != null && element.getTask().getId() == null) {
            this.projectStructurePersistenceAdapter.saveTask(SaveTaskEntityCommand.of(element.getTask()));
        }
        if (element.getPhase() != null && element.getPhase().getId() == null) {
            this.projectStructurePersistenceAdapter.savePhase(SavePhaseEntityCommand.of(element.getPhase()));
        }
        if (element.getProject() != null && element.getProject().getId() == null) {
            this.projectStructurePersistenceAdapter.saveProject(SaveProjectEntityCommand.of(element.getProject()));
        }
    }

    public void delete(AssignmentEntity element) {
        repository.delete(element);
    }

    @Override
    public Assignment findAssignmentByCloneableTemplateName(SelectAssignmentEntityCommand command) {
        List<AssignmentEntity> assignment = repository.findAssignmentByCloneableTemplateName(command.getTemplateName());
        Assignment result = null;
        if (assignment.size() >0) {
            result = assignmentMapper.toDomainObject(assignment.get(0));
        }
        return result;
    }

    @Override
    public List<AssignmentRefHitParadeProjectPhaseTask> findTopReferredProjectPhaseAndTasks() {
        List<AssignmentRefHitParadeProjectPhaseTaskEntity> topList = repository.findTopReferredProjectPhaseAndTasks();
        return topList.stream().map(t -> assignmentRefHitParadeProjectPhaseTaskEntityMapper.toDomainObject(t)).collect(Collectors.toList());
    }

    @Override
    public List<Assignment> findByEndDateIsNullOrEndTimeIsNull() {
        List<AssignmentEntity> assignment =  repository.findByEndDateIsNullOrEndTimeIsNull();

        return assignment.stream()
                .map(a -> assignmentMapper.toDomainObject(a))
                .collect(Collectors.toList());
    }

    @Override
    public Assignment selectAssignment(SelectAssignmentEntityCommand command) {
        AssignmentEntity assignment = repository.findById(command.getId()).orElse(null);
        if (assignment != null) {
            return assignmentMapper.toDomainObject(assignment);
        } else {
            return null;
        }
    }

    @Override
    public List<Assignment> selectAssignments(SelectAssignmentEntityCommand command) {
      List<AssignmentEntity> assignmentEntities =
                selectAssignmentRepository.selectAssignments(
                command.getStartDate(),
                command.getStartTime(),
                command.getEndDate(),
                command.getEndTime(),
                projectMapper.toEntityObject(command.getProject()),
                phaseMapper.toEntityObject(command.getPhase()),
                taskMapper.toEntityObject(command.getTask())

        );
        return assignmentEntities.stream()
                .map(a -> assignmentMapper.toDomainObject(a))
                .collect(Collectors.toList());
    }

    @Override
    public List<Assignment> selectAssignmentsStartDateTimeAfterGivenDateTime(SelectAssignmentEntityAfterGivenStartDateAndTimeCommand command) {
        List<AssignmentEntity> assignmentEntities =
                selectAssignmentRepository.selectAssignmentsStartDateTimeAfterGivenDateTime(
                    command.getDate(), command.getTime()
                );

        return assignmentEntities.stream()
                .map(a -> assignmentMapper.toDomainObject(a))
                .collect(Collectors.toList());
    }
}
