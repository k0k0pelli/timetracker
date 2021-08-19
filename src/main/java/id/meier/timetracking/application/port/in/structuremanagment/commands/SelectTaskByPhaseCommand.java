package id.meier.timetracking.application.port.in.structuremanagment.commands;

import id.meier.timetracking.domain.Phase;

public class SelectTaskByPhaseCommand {
    private Phase phase;
    private SelectTaskByPhaseCommand(Phase phase) {
        this.phase  = phase;
    }
    public Phase getPhase() { return this.phase;}
    public static SelectTaskByPhaseCommand of(Phase phase) {
        return new SelectTaskByPhaseCommand(phase);
    }
}
