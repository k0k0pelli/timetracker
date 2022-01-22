package id.meier.timetracking.adapter.in.web;


import id.meier.timetracking.application.port.in.structuremanagment.ManageProjectStructureUseCase;
import id.meier.timetracking.application.port.in.structuremanagment.StructureModificationCollector;
import id.meier.timetracking.application.port.in.structuremanagment.commands.*;
import id.meier.timetracking.domain.PersistableElement;
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

    public void remove(RemoveTaskCommand command) {
        manageProjectUseCase.remove(command);
    }

    public void remove(RemovePhaseCommand command) {
        manageProjectUseCase.remove(command);
    }

    public void remove(RemoveProjectCommand command) {
        manageProjectUseCase.remove(command);
    }

    public void save(SaveTaskCommand command) {
        manageProjectUseCase.save(command);
    }

    public void save(SavePhaseCommand command) {
        manageProjectUseCase.save(command);
    }

    public void save(SaveProjectCommand command) {
        manageProjectUseCase.save(command);
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

    public void saveProjectWithDependentEntities(StructureModificationCollector saveCommand) {
        manageProjectUseCase.executeCollectedCommands(saveCommand);
    }


    public Project saveProjectWithDependentEntities(SaveProjectCommand saveCommand) {
        return manageProjectUseCase.saveProjectWithDependentEntities(saveCommand);
    }

    public Phase savePhaseWithDependentEntities(SavePhaseCommand command) {
        return manageProjectUseCase.save(command);
    }

}
