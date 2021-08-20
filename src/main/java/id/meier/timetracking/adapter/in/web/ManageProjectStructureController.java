package id.meier.timetracking.adapter.in.web;


import id.meier.timetracking.application.port.in.structuremanagment.ManageProjectStructureUseCase;
import id.meier.timetracking.application.port.in.structuremanagment.commands.*;
import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManageProjectStructureController {
    private ManageProjectStructureUseCase manageProjectUseCase;


    public ManageProjectStructureController(ManageProjectStructureUseCase manageProjectUseCase) {
        this.manageProjectUseCase = manageProjectUseCase;
    }

    public Project saveProject(SaveProjectCommand command) {
        return this.manageProjectUseCase.saveProject(command);
    }

    public Task saveTask(SaveTaskCommand command) {
        return manageProjectUseCase.saveTask(command);
    }

    public Phase savePhase(SavePhaseCommand command) {
        return manageProjectUseCase.savePhase(command);
    }

    public void removeTask(RemoveTaskCommand command) {
        manageProjectUseCase.removeTask(command);
    }

    public void removePhase(RemovePhaseCommand command) {
        manageProjectUseCase.removePhase(command);
    }

    public void removeProject(RemoveProjectCommand command) {
        manageProjectUseCase.removeProject(command);
    }

    public Task getTaskById(SelectTaskByIdCommand command) {
        return manageProjectUseCase.getTaskById(command);
    }

    public List<Task> getTaskByPhase(SelectTaskByPhaseCommand command) {
        return manageProjectUseCase.getTaskByPhase(command);
    }

    public Phase getPhaseById(SelectPhaseByIdCommand command) {
        return manageProjectUseCase.getPhaseById(command);
    }

    public List<Phase> getPhaseByProject(SelectPhaseByProjectCommand project) {
        return manageProjectUseCase.getPhaseByProject(project);
    }

    public List<Project> getProjects() {
        return manageProjectUseCase.getProjects();
    }

    public Project getProject(SelectProjectCommand command) {
        return manageProjectUseCase.getProject(command);
    }

    public Project saveProjectWithDependentEntities(SaveProjectCommand command) {
        return manageProjectUseCase.saveProjectWithDependentEntities(command);
    }

    public Phase savePhaseWithDependentEntities(SavePhaseCommand command) {
        return manageProjectUseCase.savePhaseWithDependentEntities(command);
    }

}
