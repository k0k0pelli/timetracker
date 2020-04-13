package id.meier.timetracking.businesslayer.context;

import id.meier.timetracking.businesslayer.commands.DeleteCommand;
import id.meier.timetracking.businesslayer.commands.SaveCommand;
import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;

public class DefaultRepositoryContext extends RepositoryBaseContext {
    public DefaultRepositoryContext() {
        register(DeleteCommand.class, Task.class, 4);
        register(DeleteCommand.class, Phase.class, 3);
        register(DeleteCommand.class, Project.class, 2);
        register(DeleteCommand.class, Assignment.class, 1);
        register(SaveCommand.class, Task.class, 1);
        register(SaveCommand.class, Phase.class, 2);
        register(SaveCommand.class, Project.class, 3);
        register(SaveCommand.class, Assignment.class, 4);
    }
}
