package id.meier.timetracking.application.services;

import id.meier.timetracking.application.port.in.assignmentmangement.ManageAssignmentUseCase;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SaveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.RemoveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentAfterGivenStartDateAndTimeCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentCommand;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SavePhaseCommand;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SaveProjectCommand;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SaveTaskCommand;
import id.meier.timetracking.application.port.out.ManageAssignmentPort;
import id.meier.timetracking.application.port.out.SelectionAssignmentPort;
import id.meier.timetracking.application.port.out.commands.*;
import id.meier.timetracking.domain.Assignment;
import id.meier.timetracking.domain.AssignmentRefHitParadeProjectPhaseTask;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManagementAssignmentService implements ManageAssignmentUseCase {
    private ManageAssignmentPort manageAssignmentPort;
    private SelectionAssignmentPort selectionAssignmentPort;
    private ManageProjectStructureService manageProjectStructureService;

    public ManagementAssignmentService(ManageAssignmentPort manageAssignmentPort,
                                       SelectionAssignmentPort selectionAssignmentPort,
                                       ManageProjectStructureService manageProjectStructureService) {
        this.manageAssignmentPort = manageAssignmentPort;
        this.selectionAssignmentPort = selectionAssignmentPort;
        this.manageProjectStructureService = manageProjectStructureService;
    }


    @Override
    public Assignment findAssignmentByCloneableTemplateName(SelectAssignmentCommand command) {
        return selectionAssignmentPort.findAssignmentByCloneableTemplateName(
                SelectAssignmentEntityCommand.builder().setTemplateName(command.getTemplateName()).build());
    }

    @Override
    public List<AssignmentRefHitParadeProjectPhaseTask> findTopReferredProjectPhaseAndTasks() {
        return selectionAssignmentPort.findTopReferredProjectPhaseAndTasks();
    }

    @Override
    public List<Assignment> findByEndDateIsNullOrEndTimeIsNull() {
        return selectionAssignmentPort.findByEndDateIsNullOrEndTimeIsNull();
    }

    @Override
    public Assignment selectAssignment(SelectAssignmentCommand command) {
        return selectionAssignmentPort.selectAssignment(SelectAssignmentEntityCommand.builder().setId(command.getId()).build());
    }

    @Override
    public List<Assignment> selectAssignments(SelectAssignmentCommand
                                                          selectAssignmentCommand) {
        return selectionAssignmentPort.selectAssignments(
                SelectAssignmentEntityCommand.builder()
                        .setAssignmentCommand(selectAssignmentCommand).build()
        );
    }

    @Override
    public List<Assignment> selectAssignmentsStartDateTimeAfterGivenDateTime(SelectAssignmentAfterGivenStartDateAndTimeCommand command) {
        return selectionAssignmentPort.selectAssignmentsStartDateTimeAfterGivenDateTime(
                SelectAssignmentEntityAfterGivenStartDateAndTimeCommand.of(
                        command.getDate(), command.getTime()
                )
        );
    }

    @Override
    public Assignment saveAssignment(SaveAssignmentCommand assignmentCommand) {
        return manageAssignmentPort.saveAssignment(SaveAssignmentEntityCommand.of(assignmentCommand.getAssignment()));

    }

    @Override
    public void removeAssignment(RemoveAssignmentCommand assignmentCommand) {
        manageAssignmentPort.removeAssignment(RemoveAssignmentEntityCommand.of(assignmentCommand.getAssignment()));
    }

    @Override
    public Assignment saveAssignmentWithDependentEntities(SaveAssignmentCommand assignmentCommand) {
        Assignment assignment = assignmentCommand.getAssignment();
        manageProjectStructureService.save(SaveTaskCommand.of(assignment.getTask()));
        manageProjectStructureService.save(SavePhaseCommand.of(assignment.getPhase()));
        manageProjectStructureService.save(SaveProjectCommand.of(assignment.getProject()));
        return manageAssignmentPort.saveAssignment(SaveAssignmentEntityCommand.of(assignmentCommand.getAssignment()));
    }

}
