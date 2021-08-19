package id.meier.timetracking.application.port.in.structuremanagment.commands;

import id.meier.timetracking.domain.Project;

public class SelectPhaseByIdCommand {
    private Long id;
    private Project project;
    private SelectPhaseByIdCommand(Long id) {
        this.id  = id;
    }
    private SelectPhaseByIdCommand(Project project) {
        this.project  = project;
    }

    public Long getId() {
        return this.id;
    }
    public Project getProject() {return this.project;}


    public static SelectPhaseByIdCommand of(Long id) {
        return new SelectPhaseByIdCommand(id);
    }
    public static SelectPhaseByIdCommand of(Project project) {
        return new SelectPhaseByIdCommand(project);
    }
}
