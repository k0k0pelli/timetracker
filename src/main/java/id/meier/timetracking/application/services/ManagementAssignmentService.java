package id.meier.timetracking.application.services;

import id.meier.timetracking.application.port.in.assignmentmangement.ManageAssignmentUseCase;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SaveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.RemoveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentAfterGivenStartDateAndTimeCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentCommand;
import id.meier.timetracking.application.port.out.ManageAssignmentPort;
import id.meier.timetracking.application.port.out.SelectionAssignmentPort;
import id.meier.timetracking.application.port.out.commands.RemoveAssignmentEntityCommand;
import id.meier.timetracking.application.port.out.commands.SaveAssignmentEntityCommand;
import id.meier.timetracking.application.port.out.commands.SelectAssignmentEntityAfterGivenStartDateAndTimeCommand;
import id.meier.timetracking.application.port.out.commands.SelectAssignmentEntityCommand;
import id.meier.timetracking.domain.Assignment;
import id.meier.timetracking.domain.AssignmentRefHitParadeProjectPhaseTask;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManagementAssignmentService implements ManageAssignmentUseCase {
    private ManageAssignmentPort manageAssignmentPort;
    private SelectionAssignmentPort selectionAssignmentPort;


    public ManagementAssignmentService(ManageAssignmentPort manageAssignmentPort,
                                       SelectionAssignmentPort selectionAssignmentPort) {
        this.manageAssignmentPort = manageAssignmentPort;
        this.selectionAssignmentPort = selectionAssignmentPort;
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
}
