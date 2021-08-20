package id.meier.timetracking.application.port.out;


import id.meier.timetracking.adapter.out.persistence.entities.AssignmentRefHitParadeProjectPhaseTaskEntity;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentCommand;
import id.meier.timetracking.application.port.out.commands.SelectAssignmentEntityAfterGivenStartDateAndTimeCommand;
import id.meier.timetracking.application.port.out.commands.SelectAssignmentEntityCommand;
import id.meier.timetracking.domain.Assignment;
import id.meier.timetracking.domain.AssignmentRefHitParadeProjectPhaseTask;

import java.util.List;

public interface SelectionAssignmentPort {
    Assignment findAssignmentByCloneableTemplateName(SelectAssignmentEntityCommand command);
    List<AssignmentRefHitParadeProjectPhaseTask> findTopReferredProjectPhaseAndTasks();
    List<Assignment> findByEndDateIsNullOrEndTimeIsNull();
    Assignment selectAssignment(SelectAssignmentEntityCommand command);
    List<Assignment> selectAssignments(SelectAssignmentEntityCommand command);
    List<Assignment> selectAssignmentsStartDateTimeAfterGivenDateTime(SelectAssignmentEntityAfterGivenStartDateAndTimeCommand command);
}
