package id.meier.timetracking.application.port.in.structuremanagment.commands;

public class SelectProjectCommand {
    private Long id;
    private SelectProjectCommand(Long id) {
        this.id  = id;
    }

    public Long getId() {
        return this.id;
    }

    public static SelectProjectCommand of(Long id) {
        return new SelectProjectCommand(id);
    }

}
