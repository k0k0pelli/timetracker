package id.meier.timetracking.web.projects;


import id.meier.timetracking.adapter.in.web.ManageProjectStructureController;
import id.meier.timetracking.application.port.in.structuremanagment.StructureModificationCollector;
import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Task;

public class TaskElementView extends SubElementView<Task, Phase> {
	private final ManageProjectStructureController projectStructureController;
	TaskElementView(ManageProjectStructureController projectStructureController, SubElementEditor<Task> editor, StructureModificationCollector modificationCommandsCollector) {
		super(false, Task.class, editor, modificationCommandsCollector);
		editor.setBaseEditorChangeHandler(this);
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
	protected String getSaveLabel() {
		return getTranslation("time.tracking.task.management.save");
	}
	
	@Override
	protected String getAddNewElementLabel() {
		return getTranslation("time.tracking.task.management.create");
	}

	@Override
	public void addElement(Task element) {
		this.getModificationCommandsCollector().addElementForSaveIfNotContained(element);
		setFilteredItems(this.allElements);
	}

	@Override
	public void removeElement(Task element) {
		this.getModificationCommandsCollector().addElementForRemoveIfNotContained(element);
		setFilteredItems(allElements);
	}

    @Override
    public void onChange(ChangeAction action, Task element) {

    }

    public void setVisible(boolean visible) {
		super.setVisible(visible);
	}


}
