package id.meier.timetracking.adapter.in.web;

import id.meier.timetracking.application.port.in.assignmentmangement.ManageAssignmentUseCase;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SaveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.RemoveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentAfterGivenStartDateAndTimeCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentCommand;
import id.meier.timetracking.domain.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class ManageAssignmentController {
    private ManageAssignmentUseCase manageAssignmentUseCase;

    public ManageAssignmentController(ManageAssignmentUseCase manageAssignmentUseCase) {
        this.manageAssignmentUseCase = manageAssignmentUseCase;
    }

    public Assignment selectAssignment(SelectAssignmentCommand selectCommand) {
        return manageAssignmentUseCase.selectAssignment(selectCommand);
    }

    public List<Assignment> selectAssignments(SelectAssignmentCommand selectCommand) {
        return manageAssignmentUseCase.selectAssignments(selectCommand);
    }

    public List<Assignment> findByEndDateIsNullOrEndTimeIsNull() {
        return manageAssignmentUseCase.findByEndDateIsNullOrEndTimeIsNull();
    }

    public List<AssignmentRefHitParadeProjectPhaseTask> findTopReferredProjectPhaseAndTasks() {
        return manageAssignmentUseCase.findTopReferredProjectPhaseAndTasks();
    }
    public Assignment findAssignmentByCloneableTemplateName(SelectAssignmentCommand command) {
        return manageAssignmentUseCase.findAssignmentByCloneableTemplateName(command);
    }

    public List<Assignment> selectAssignmentsStartDateTimeAfterGivenDateTime(SelectAssignmentAfterGivenStartDateAndTimeCommand command) {
        return manageAssignmentUseCase.selectAssignmentsStartDateTimeAfterGivenDateTime(
                command
        );
    }

    public Assignment saveAssignment(SaveAssignmentCommand assignmentCommand) {
        return manageAssignmentUseCase.saveAssignment(assignmentCommand);
    }

    public void removeAssignment(RemoveAssignmentCommand command) {
        manageAssignmentUseCase.removeAssignment(command);
    }

    public Assignment saveAssignmentWithDependentEntities(SaveAssignmentCommand assignmentCommand) {
        return manageAssignmentUseCase.saveAssignmentWithDependentEntities(assignmentCommand);
    }
}
