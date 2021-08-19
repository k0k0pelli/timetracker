package id.meier.timetracking.application.port.in.structuremanagment.commands;

import id.meier.timetracking.domain.Phase;

public class SelectTaskByIdCommand {
    private Long id;
    private SelectTaskByIdCommand(Long id) {
        this.id  = id;
    }
    public Long getId() {
        return this.id;
    }
    public static SelectTaskByIdCommand of(Long id) {
        return new SelectTaskByIdCommand(id);
    }

}
