package id.meier.timetracking.ui.projects;

import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.db.repository.RepositoryAccessor;
import id.meier.timetracking.db.entity.TaskEntity;

public class TaskElementEditor extends SubElementEditor<TaskEntity> {
	TaskElementEditor(CommandsCollector commandsCollector, RepositoryAccessor repoAccessor) {
		super(commandsCollector, TaskEntity.class);
	}

	@Override
	protected String getNameLabel() {
		return getTranslation("time.tracking.task.management.name");
	}

	@Override
	protected String getDescriptionLabel() {
		return getTranslation("time.tracking.task.management.description");
	}

	@Override
	protected String getDeleteLabel() {
		return getTranslation("time.tracking.task.management.delete");
	}

	@Override
	protected String getCancelLabel() {
		return getTranslation("time.tracking.task.management.cancel");
	}

	@Override
	protected String getSaveLabel() {
		return getTranslation("time.tracking.task.management.save");
	}

	@Override
	protected String getActiveLabel() {
		return getTranslation("time.tracking.task.management.active");
	}

	@Override
	protected void deleteEditedElement(TaskEntity task) {
		commandsCollector.removeTaskFromAssignment(task);
		commandsCollector.removeTaskFromProject(task);
		super.deleteEditedElement(task);
	}
	

	

}
