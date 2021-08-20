package id.meier.timetracking.application.port.out;

import id.meier.timetracking.application.port.out.commands.*;
import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;

public interface ManageProjectStructurePort {
    Project saveProject(SaveProjectEntityCommand command);
    Phase savePhase(SavePhaseEntityCommand command);
    Task saveTask(SaveTaskEntityCommand taskCommand);


    void removeProject(RemoveProjectEntityCommand command);
    void removePhase(RemovePhaseEntityCommand command);
    void removeTask(RemoveTaskEntityCommand command);
}
