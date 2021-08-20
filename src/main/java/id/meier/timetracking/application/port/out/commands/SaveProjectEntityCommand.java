package id.meier.timetracking.application.port.out.commands;

import id.meier.timetracking.domain.Project;

public class SaveProjectEntityCommand {
	private final Project project;

	private SaveProjectEntityCommand(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

	public static SaveProjectEntityCommand of(Project project) {
		return new SaveProjectEntityCommand(project);
	}

}
