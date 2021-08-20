package id.meier.timetracking.application.port.out;


import id.meier.timetracking.application.port.out.commands.SelectPhaseEntityCommand;
import id.meier.timetracking.application.port.out.commands.SelectProjectEntityCommand;
import id.meier.timetracking.application.port.out.commands.SelectTaskEntityCommand;
import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;

import java.util.List;

public interface SelectionProjectStructurePort {
    Project selectProject(SelectProjectEntityCommand command);
    List<Project> selectAllProjects();
    List<Phase> selectPhases(SelectPhaseEntityCommand command);
    List<Task> selectTask(SelectTaskEntityCommand command);

}
