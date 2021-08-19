package id.meier.timetracking.application.port.in.structuremanagment.commands;

import id.meier.timetracking.domain.Project;

public class SelectPhaseByProjectCommand {
    private Project project;
    private SelectPhaseByProjectCommand(Project project) {
        this.project  = project;
    }
    public Project getProject() {return this.project;}
    public static SelectPhaseByProjectCommand of(Project project) {
        return new SelectPhaseByProjectCommand(project);
    }
}
