package id.meier.timetracking.application.port.in.structuremanagment;

import id.meier.timetracking.application.port.in.structuremanagment.commands.*;
import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;

import java.util.List;

public interface ManageProjectStructureUseCase {
    Project save(SaveProjectCommand command);
    Task save(SaveTaskCommand command);
    Phase save(SavePhaseCommand command);
    void remove(RemoveTaskCommand command);
    void remove(RemovePhaseCommand command);
    void remove(RemoveProjectCommand command);
    Task getTaskById(SelectTaskByIdCommand command);
    List<Task> getTaskByPhase(SelectTaskByPhaseCommand command);
    Phase getPhaseById(SelectPhaseByIdCommand command);
    List<Phase> getPhaseByProject(SelectPhaseByProjectCommand project);
    List<Project> getProjects();
    Project getProject(SelectProjectCommand command);
    Project saveProjectWithDependentEntities(SaveProjectCommand command);
    Phase savePhaseWithDependentEntities(SavePhaseCommand command);
    void executeCollectedCommands(StructureModificationCollector collector);
}
