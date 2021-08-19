package id.meier.timetracking.application.port.in.structuremanagment;

import id.meier.timetracking.application.port.in.structuremanagment.commands.*;
import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;

import java.util.List;

public interface ManageProjectStructureUseCase {
    Project saveProject(SaveProjectCommand command);
    Task saveTask(SaveTaskCommand command);
    Phase savePhase(SavePhaseCommand command);
    void removeTask(RemoveTaskCommand command);
    void removePhase(RemovePhaseCommand command);
    void removeProject(RemoveProjectCommand command);
    Task getTaskById(SelectTaskByIdCommand command);
    List<Task> getTaskByPhase(SelectTaskByPhaseCommand command);
    Phase getPhaseById(SelectPhaseByIdCommand command);
    List<Phase> getPhaseByProject(SelectPhaseByProjectCommand project);
    List<Project> getProjects();
    Project getProject(SelectProjectCommand command);
}
