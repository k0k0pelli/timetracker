package id.meier.timetracking.ui.projects;

import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Task;

public class TaskElementView extends SubElementView<Task, Phase> {
	
	TaskElementView(CommandsCollector commandsCollector, SubElementEditor<Task> editor) {
		super(false, commandsCollector, Task.class, editor);
		editor.setBaseEditorChangeHandler(this);
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
			commandsCollector.addTaskToPhase(element, this.parent);
			commandsCollector.save(element);
		}
		setFilteredItems(this.allElements);
	}

	@Override
	public void removeElement(Task element) {
		if (!removedElements.contains(element)) {
			removedElements.add(element);
		}
		removedElements.add(element);
		setFilteredItems(allElements);
	}

    @Override
    public void onChange(ChangeAction action) {

    }

    public void setVisible(boolean visible) {
		super.setVisible(visible);
	}
}
