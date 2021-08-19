package id.meier.timetracking.application.port.in.structuremanagment.commands;

import id.meier.timetracking.domain.Phase;

public class RemovePhaseCommand {
    private Phase phase;
    private RemovePhaseCommand(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return this.phase;
    }

    public static RemovePhaseCommand of(Phase phase) {
        return new RemovePhaseCommand(phase);
    }
}
