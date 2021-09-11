package id.meier.timetracking.web.projects;


import id.meier.timetracking.adapter.in.web.ManageProjectStructureController;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SaveTaskCommand;
import id.meier.timetracking.domain.DescribedElement;
import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Task;

public class TaskElementView extends SubElementView<Task, Phase> {
	private final ManageProjectStructureController projectStructureController;
	TaskElementView(ManageProjectStructureController projectStructureController, SubElementEditor<Task> editor) {
		super(false, Task.class, editor);
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
		if (!addedElements.contains(element)) {
			this.addedElements.add(element);
			parent.getTasks().add(element);
		}
		setFilteredItems(this.allElements);
	}

	@Override
	public void removeElement(Task element) {
		if (!removedElements.contains(element)) {
			removedElements.add(element);
			parent.getTasks().remove(element);
		}

		removedElements.add(element);
		setFilteredItems(allElements);
	}

    @Override
    public void onChange(ChangeAction action, Task element) {

    }

    public void setVisible(boolean visible) {
		super.setVisible(visible);
	}


}
