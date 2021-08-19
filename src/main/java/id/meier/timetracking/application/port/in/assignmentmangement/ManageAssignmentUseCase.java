package id.meier.timetracking.application.port.in.assignmentmangement;

import id.meier.timetracking.application.port.in.assignmentmangement.commands.SaveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.RemoveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentAfterGivenStartDateAndTimeCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentCommand;
import id.meier.timetracking.domain.Assignment;
import id.meier.timetracking.domain.AssignmentRefHitParadeProjectPhaseTask;

import java.util.List;

public interface ManageAssignmentUseCase {
    Assignment findAssignmentByCloneableTemplateName(SelectAssignmentCommand command);
    List<AssignmentRefHitParadeProjectPhaseTask> findTopReferredProjectPhaseAndTasks();
    List<Assignment> findByEndDateIsNullOrEndTimeIsNull();
    Assignment selectAssignment(SelectAssignmentCommand command);
    List<Assignment> selectAssignments(SelectAssignmentCommand selectAssignmentCommand);
    List<Assignment>selectAssignmentsStartDateTimeAfterGivenDateTime(SelectAssignmentAfterGivenStartDateAndTimeCommand command);
    Assignment saveAssignment(SaveAssignmentCommand assignmentCommand);
    void removeAssignment(RemoveAssignmentCommand assignmentCommand);

}
