package id.meier.timetracking.application.port.in.structuremanagment.commands;

import id.meier.timetracking.domain.Project;

public class SaveProjectCommand {
    private Project project;
    private SaveProjectCommand(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return this.project;
    }

    public static SaveProjectCommand of(Project project) {
        return new SaveProjectCommand(project);
    }
}
