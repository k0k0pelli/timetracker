package id.meier.timetracking.businesslayer.context;

import id.meier.timetracking.businesslayer.commands.DeleteCommand;
import id.meier.timetracking.businesslayer.commands.SaveCommand;
import id.meier.timetracking.db.entity.AssignmentEntity;
import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.ProjectEntity;
import id.meier.timetracking.db.entity.TaskEntity;

public class DefaultRepositoryContext extends RepositoryBaseContext {
    public DefaultRepositoryContext() {
        register(DeleteCommand.class, TaskEntity.class, 4);
        register(DeleteCommand.class, PhaseEntity.class, 3);
        register(DeleteCommand.class, ProjectEntity.class, 2);
        register(DeleteCommand.class, AssignmentEntity.class, 1);
        register(SaveCommand.class, TaskEntity.class, 1);
        register(SaveCommand.class, PhaseEntity.class, 2);
        register(SaveCommand.class, ProjectEntity.class, 3);
        register(SaveCommand.class, AssignmentEntity.class, 4);
    }
}
