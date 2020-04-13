package id.meier.timetracking.ui.projects;

import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;

public class PhaseElementView extends SubElementView<Phase, Project> {
	PhaseElementView(CommandsCollector commandsCollector, SubElementEditor<Phase> editor) {
		super(false, commandsCollector, Phase.class, editor);
		editor.setBaseEditorChangeHandler(this);
	}
	
	@Override
	protected String getNameLabel() {
		return getTranslation("time.tracking.phase.management.name");
	}
	
	@Override
	protected String getDescriptionLabel() {
		return getTranslation("time.tracking.phase.management.description");
	}

	@Override
	protected String getSaveLabel() {
		return getTranslation("time.tracking.phase.management.save");
	}
	
	@Override
	protected String getAddNewElementLabel() {
		return getTranslation("time.tracking.phase.management.create");
	}

	@Override
	public void addElement(Phase element) {
		if (!addedElements.contains(element)) {
			this.addedElements.add(element);
			commandsCollector.addPhaseToProject(element, this.parent);
			commandsCollector.save(element);
		}
		setFilteredItems(this.allElements);
	}

	@Override
	public void removeElement(Phase element) {
		if (!removedElements.contains(element)) {
			removedElements.add(element);
		}
		removedElements.add(element);
		setFilteredItems(allElements);
	}

    @Override
    public void onChange(ChangeAction action) {

    }


}