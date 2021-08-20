package id.meier.timetracking.web.projects;

import id.meier.timetracking.adapter.in.web.ManageProjectStructureController;
import id.meier.timetracking.application.port.in.structuremanagment.commands.RemoveTaskCommand;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SaveTaskCommand;
import id.meier.timetracking.domain.Task;

public class TaskElementEditor extends SubElementEditor<Task> {
	private final ManageProjectStructureController projectStructureController;
	TaskElementEditor(ManageProjectStructureController projectStructureController) {
		super(Task.class);
		this.projectStructureController = projectStructureController;
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
	protected void saveElement(Task element) {
		//projectStructureController.saveTask(SaveTaskCommand.of(element));
	}

	@Override
	protected void deleteElement(Task element) {
		//projectStructureController.removeTask(RemoveTaskCommand.of(element));
	}


}
