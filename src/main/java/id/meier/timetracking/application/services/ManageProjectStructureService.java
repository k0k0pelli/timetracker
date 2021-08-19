package id.meier.timetracking.application.services;

import id.meier.timetracking.application.port.in.structuremanagment.ManageProjectStructureUseCase;
import id.meier.timetracking.application.port.in.structuremanagment.commands.*;
import id.meier.timetracking.application.port.out.ManageProjectStructurePort;
import id.meier.timetracking.application.port.out.SelectionProjectStructurePort;
import id.meier.timetracking.application.port.out.commands.*;
import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ManageProjectStructureService implements ManageProjectStructureUseCase {

    private ManageProjectStructurePort manageProjectStructurePort;
    private SelectionProjectStructurePort selectionProjectStructurePort;

    public ManageProjectStructureService(ManageProjectStructurePort manageProjectStructurePort,
                                         SelectionProjectStructurePort selectionProjectStructurePort) {
        this.manageProjectStructurePort = manageProjectStructurePort;
        this.selectionProjectStructurePort = selectionProjectStructurePort;
    }

    @Override
    public Project saveProject(SaveProjectCommand command) {
        Project p = command.getProject();
        p.getPhases().forEach(phase -> checkForNonPersistedPhaseAndSave(phase));

        // create save command and execute on db
        return manageProjectStructurePort.saveProject(SaveProjectEntityCommand.of(p));
    }

    private void checkForNonPersistedPhaseAndSave(Phase phase) {
        if (phase.getId() == null)  {
            savePhase(SavePhaseCommand.of(phase));
        }
    }

    @Override
    public Phase  savePhase(SavePhaseCommand command) {
        Phase p = command.getPhase();
        p.getTasks().forEach(this::checkForNonPersistedTaskAndSave);

        // create save command and execute on db
        return manageProjectStructurePort.savePhase(SavePhaseEntityCommand.of(p));
    }

    private void checkForNonPersistedTaskAndSave(Task task) {
        if (task.getId() == null)  {
            saveTask(SaveTaskCommand.of(task));
        }
    }

    @Override
    public Task saveTask(SaveTaskCommand command) {
        // create save command and execute on db
        return manageProjectStructurePort.saveTask(SaveTaskEntityCommand.of(command.getTask()));
    }

    @Override
    public void removeProject(RemoveProjectCommand command) {
        Project p = command.getProject();
        List<Phase> phaseList = new ArrayList<>(p.getPhases());
        p.getPhases().clear();
        saveProject(SaveProjectCommand.of(p));
        phaseList.forEach(phase -> {
            removePhase(RemovePhaseCommand.of(phase));
        });
        manageProjectStructurePort.removeProject(RemoveProjectEntityCommand.of(p));
    }

    @Override
    public void removePhase(RemovePhaseCommand command) {
        // delete all tasks
        Phase p = command.getPhase();
        List<Task> taskList = new ArrayList<>(p.getTasks());
        p.getTasks().clear();
        savePhase(SavePhaseCommand.of(p));
        taskList.forEach(t -> {
            removeTask(RemoveTaskCommand.of(t));
        });
        manageProjectStructurePort.removePhase(RemovePhaseEntityCommand.of(p));
    }

    @Override
    public void removeTask(RemoveTaskCommand command) {
        manageProjectStructurePort.removeTask(RemoveTaskEntityCommand.of(command.getTask()));
    }


    @Override
    public Task getTaskById(SelectTaskByIdCommand command) {
        List<Task> tasks = selectionProjectStructurePort.selectTask(SelectTaskEntityCommand.of(command.getId()));
        return (tasks.size() > 0)?tasks.get(0):null;
    }

    @Override
    public List<Task> getTaskByPhase(SelectTaskByPhaseCommand command) {
        return selectionProjectStructurePort.selectTask(SelectTaskEntityCommand.of(command.getPhase()));
    }

    @Override
    public Phase getPhaseById(SelectPhaseByIdCommand command) {
        List<Phase> phases = selectionProjectStructurePort.selectPhases(SelectPhaseEntityCommand.of(command.getId()));
        return (phases.size() > 0)?phases.get(0):null;
    }

    @Override
    public List<Phase> getPhaseByProject(SelectPhaseByProjectCommand command) {
        return selectionProjectStructurePort.selectPhases(SelectPhaseEntityCommand.of(command.getProject()));
    }

    @Override
    public List<Project> getProjects() {
        return selectionProjectStructurePort.selectAllProjects();
    }

    @Override
    public Project getProject(SelectProjectCommand command) {
        return selectionProjectStructurePort.selectProject(SelectProjectEntityCommand.of(command.getId()));
    }


}
