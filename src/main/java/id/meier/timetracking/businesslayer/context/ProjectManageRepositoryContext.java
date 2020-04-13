package id.meier.timetracking.businesslayer.context;

import id.meier.timetracking.businesslayer.commands.DeleteCommand;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;

public class ProjectManageRepositoryContext extends RepositoryBaseContext {
    public ProjectManageRepositoryContext() {
        register(DeleteCommand.class, Task.class, 4);
        register(DeleteCommand.class, Phase.class, 3);
        register(DeleteCommand.class, Project.class, 2);
    }
}


