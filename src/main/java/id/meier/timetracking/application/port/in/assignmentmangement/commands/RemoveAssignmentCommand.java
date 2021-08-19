package id.meier.timetracking.application.port.in.assignmentmangement.commands;

import id.meier.timetracking.domain.Assignment;

public class RemoveAssignmentCommand {
    private Assignment assignment;
    private RemoveAssignmentCommand(Assignment assignment) {
        this.assignment = assignment;
    }

    public Assignment getAssignment() {
        return this.assignment;
    }

    public static RemoveAssignmentCommand of(Assignment assignment) {
        return new RemoveAssignmentCommand(assignment);
    }
}
