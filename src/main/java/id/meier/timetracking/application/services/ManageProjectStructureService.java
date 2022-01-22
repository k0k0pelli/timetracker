package id.meier.timetracking.application.services;

import id.meier.timetracking.application.port.in.structuremanagment.ManageProjectStructureUseCase;
import id.meier.timetracking.application.port.in.structuremanagment.StructureModificationCollector;
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
    public Project save(SaveProjectCommand command) {
        Project p = command.getProject();
        p.getPhases().forEach(phase -> checkForNonPersistedPhaseAndSave(phase));

        // create save command and execute on db
        return manageProjectStructurePort.saveProject(SaveProjectEntityCommand.of(p));
    }

    private void checkForNonPersistedPhaseAndSave(Phase phase) {
        if (phase.getId() == null)  {
            save(SavePhaseCommand.of(phase));
        }
    }

    @Override
    public Phase save(SavePhaseCommand command) {
        Phase p = command.getPhase();
        p.getTasks().forEach(this::checkForNonPersistedTaskAndSave);

        // create save command and execute on db
        return manageProjectStructurePort.savePhase(SavePhaseEntityCommand.of(p));
    }

    private void checkForNonPersistedTaskAndSave(Task task) {
        if (task.getId() == null)  {
            save(SaveTaskCommand.of(task));
        }
    }

    @Override
    public Task save(SaveTaskCommand command) {
        // create save command and execute on db
        return manageProjectStructurePort.saveTask(SaveTaskEntityCommand.of(command.getTask()));
    }

    @Override
    public void remove(RemoveProjectCommand command) {
        Project p = command.getProject();
        List<Phase> phaseList = new ArrayList<>(p.getPhases());
        p.getPhases().clear();
        save(SaveProjectCommand.of(p));
        phaseList.forEach(phase -> {
            remove(RemovePhaseCommand.of(phase));
        });
        manageProjectStructurePort.removeProject(RemoveProjectEntityCommand.of(p));
    }

    @Override
    public void remove(RemovePhaseCommand command) {
        // delete all tasks
        Phase p = command.getPhase();
        List<Task> taskList = new ArrayList<>(p.getTasks());
        p.getTasks().clear();
        save(SavePhaseCommand.of(p));
        taskList.forEach(t -> {
            remove(RemoveTaskCommand.of(t));
        });
        manageProjectStructurePort.removePhase(RemovePhaseEntityCommand.of(p));
    }

    @Override
    public void remove(RemoveTaskCommand command) {
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

    @Override
    public Project saveProjectWithDependentEntities(SaveProjectCommand command) {
        Project p = command.getProject();
        p.getPhases().forEach(phase -> savePhaseWithDependentEntities(SavePhaseCommand.of(phase)));
        return save(command);
    }

    @Override
    public Phase savePhaseWithDependentEntities(SavePhaseCommand command) {
        Phase phase = command.getPhase();
        phase.getTasks().forEach(t -> save(SaveTaskCommand.of(t)));
        return manageProjectStructurePort.savePhase(SavePhaseEntityCommand.of(phase));
    }

    @Override
    public void executeCollectedCommands(StructureModificationCollector collector) {
        removeElements(collector);
        saveElements(collector);
        collector.clear();
    }

    private void removeElements(StructureModificationCollector collector) {
        List<Task> removedTasks = collector.getRemovedElements(Task.class);
        removeTasks(removedTasks);
        List<Phase> removedPhases = collector.getRemovedElements(Phase.class);
        removePhases(removedPhases);
        List<Project> removedProject = collector.getRemovedElements(Project.class);
        removeProjects(removedProject);
    }

    private void removeTasks(List<Task> removedTasks) {
        removedTasks.stream().forEach(t -> this.remove(RemoveTaskCommand.of(t)));
    }

    private void removePhases(List<Phase> removedPhase) {
        removedPhase.stream().forEach(p -> this.remove(RemovePhaseCommand.of(p)));
    }

    private void removeProjects(List<Project> removedProject) {
        removedProject.stream().forEach(p -> this.remove(RemoveProjectCommand.of(p)));
    }

    private void saveElements(StructureModificationCollector collector) {
        List<Task> savedTasks = collector.getSavedElements(Task.class);
        saveTasks(savedTasks);
        List<Phase> savedPhases = collector.getSavedElements(Phase.class);
        savePhases(savedPhases);
        List<Project> savedProject = collector.getSavedElements(Project.class);
        saveProjects(savedProject);
    }

    private void saveTasks(List<Task> savedTasks) {
        savedTasks.stream().forEach(t -> this.save((SaveTaskCommand.of(t))));
    }

    private void savePhases(List<Phase> savedPhases) {
        savedPhases.stream().forEach(p -> this.save(SavePhaseCommand.of(p)));
    }

    private void saveProjects(List<Project> removedProjects) {
        removedProjects.stream().forEach(p -> this.save(SaveProjectCommand.of(p)));
    }
}
