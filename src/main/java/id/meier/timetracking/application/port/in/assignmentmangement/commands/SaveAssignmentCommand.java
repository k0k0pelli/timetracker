package id.meier.timetracking.application.port.in.assignmentmangement.commands;

import id.meier.timetracking.domain.Assignment;

public class SaveAssignmentCommand {
    private Assignment assignment;
    private SaveAssignmentCommand(Assignment assignment) {
        this.assignment = assignment;
    }

    public Assignment getAssignment() {
        return this.assignment;
    }

    public static SaveAssignmentCommand of(Assignment assignment) {
        return new SaveAssignmentCommand(assignment);
    }
}
