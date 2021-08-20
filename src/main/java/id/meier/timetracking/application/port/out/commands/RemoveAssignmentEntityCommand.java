package id.meier.timetracking.application.port.out.commands;

import id.meier.timetracking.domain.Assignment;
import id.meier.timetracking.domain.Project;

public class RemoveAssignmentEntityCommand {
	private final Assignment assignment;

	private RemoveAssignmentEntityCommand(Assignment assignment) {
		this.assignment = assignment;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public static RemoveAssignmentEntityCommand of(Assignment assignment) {
		return new RemoveAssignmentEntityCommand(assignment);
	}

}
