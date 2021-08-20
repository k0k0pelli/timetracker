package id.meier.timetracking.application.port.out.commands;

import id.meier.timetracking.domain.Assignment;
import id.meier.timetracking.domain.Project;

public class SaveAssignmentEntityCommand {
	private final Assignment assignment;

	private SaveAssignmentEntityCommand(Assignment assignment) {
		this.assignment = assignment;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public static SaveAssignmentEntityCommand of(Assignment assignment) {
		return new SaveAssignmentEntityCommand(assignment);
	}

}
