package id.meier.timetracking.application.port.out;

import id.meier.timetracking.application.port.in.assignmentmangement.commands.SaveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.RemoveAssignmentCommand;
import id.meier.timetracking.application.port.out.commands.RemoveAssignmentEntityCommand;
import id.meier.timetracking.application.port.out.commands.SaveAssignmentEntityCommand;
import id.meier.timetracking.domain.Assignment;

public interface ManageAssignmentPort {
    Assignment saveAssignment(SaveAssignmentEntityCommand assignmentCommand);

    void removeAssignment(RemoveAssignmentEntityCommand assignmentCommand);
}
