package id.meier.timetracking.businesslayer.context;

import id.meier.timetracking.businesslayer.commands.DeleteCommand;
import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.ProjectEntity;
import id.meier.timetracking.db.entity.TaskEntity;

public class ProjectManageRepositoryContext extends RepositoryBaseContext {
    public ProjectManageRepositoryContext() {
        register(DeleteCommand.class, TaskEntity.class, 4);
        register(DeleteCommand.class, PhaseEntity.class, 3);
        register(DeleteCommand.class, ProjectEntity.class, 2);
    }
}


