package id.meier.timetracking.application.port.out.commands;

import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;

import java.util.Optional;

public class SelectPhaseEntityCommand {
	private  Project project;
	private  Long id;

	private SelectPhaseEntityCommand(Project phase) {
		this.project = project;
	}
	private SelectPhaseEntityCommand(Long id) {
		this.id = id;
	}

	public Optional<Project> getProject() {
		return Optional.ofNullable(project);
	}
	public Optional<Long> getId() { return Optional.ofNullable(id); }

	public static SelectPhaseEntityCommand of(Project project) {
		return new SelectPhaseEntityCommand(project);
	}

	public static SelectPhaseEntityCommand of(Long id) {
		return new SelectPhaseEntityCommand(id);
	}

}
