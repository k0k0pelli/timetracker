package id.meier.timetracking.application.port.out.commands;

import id.meier.timetracking.domain.Phase;

public class SavePhaseEntityCommand {
	private final Phase phase;

	private SavePhaseEntityCommand(Phase phase) {
		this.phase = phase;
	}

	public Phase getPhase() {
		return phase;
	}

	public static SavePhaseEntityCommand of(Phase phase) {
		return new SavePhaseEntityCommand(phase);
	}

}
