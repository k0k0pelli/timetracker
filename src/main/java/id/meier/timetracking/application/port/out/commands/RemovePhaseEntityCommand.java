package id.meier.timetracking.application.port.out.commands;

import id.meier.timetracking.domain.Phase;

public class RemovePhaseEntityCommand {
	private final Phase phase;

	private RemovePhaseEntityCommand(Phase phase) {
		this.phase = phase;
	}

	public Phase getPhase() {
		return phase;
	}

	public static RemovePhaseEntityCommand of(Phase phase) {
		return new RemovePhaseEntityCommand(phase);
	}

}
