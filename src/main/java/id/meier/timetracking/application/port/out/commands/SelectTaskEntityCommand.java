package id.meier.timetracking.application.port.out.commands;

import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;

import java.util.Optional;

public class SelectTaskEntityCommand {
	private  Phase phase;
	private  Long id;

	private SelectTaskEntityCommand(Phase phase) {
		this.phase = phase;
	}
	private SelectTaskEntityCommand(Long id) {
		this.id = id;
	}

	public Optional<Phase> getPhase() {
		return Optional.ofNullable(phase);
	}
	public Optional<Long> getId() { return Optional.ofNullable(id); }

	public static SelectTaskEntityCommand of(Phase phase) {
		return new SelectTaskEntityCommand(phase);
	}

	public static SelectTaskEntityCommand of(Long id) {
		return new SelectTaskEntityCommand(id);
	}
}
