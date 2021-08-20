package id.meier.timetracking.application.port.out.commands;

import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;

public class RemoveProjectEntityCommand {
	private final Project project;

	private RemoveProjectEntityCommand(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

	public static RemoveProjectEntityCommand of(Project project) {
		return new RemoveProjectEntityCommand(project);
	}

}
