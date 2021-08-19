package id.meier.timetracking.application.port.in.structuremanagment.commands;

import id.meier.timetracking.domain.Phase;

public class SavePhaseCommand {
    private Phase phase;
    private SavePhaseCommand(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return this.phase;
    }

    public static SavePhaseCommand of(Phase phase) {
        return new SavePhaseCommand(phase);
    }
}
