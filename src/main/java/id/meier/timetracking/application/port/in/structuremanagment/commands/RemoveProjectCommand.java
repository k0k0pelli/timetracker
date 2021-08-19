package id.meier.timetracking.application.port.in.structuremanagment.commands;

import id.meier.timetracking.domain.Project;

public class RemoveProjectCommand {
    private Project project;
    private RemoveProjectCommand(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return this.project;
    }

    public static RemoveProjectCommand of(Project project) {
        return new RemoveProjectCommand(project);
    }
}
