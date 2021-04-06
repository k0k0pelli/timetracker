package id.meier.timetracking.ui.projects;

import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.ProjectEntity;

public class PhaseElementView extends SubElementView<PhaseEntity, ProjectEntity> {
	PhaseElementView(CommandsCollector commandsCollector, SubElementEditor<PhaseEntity> editor) {
		super(false, commandsCollector, PhaseEntity.class, editor);
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
	public void addElement(PhaseEntity element) {
		if (!addedElements.contains(element)) {
			this.addedElements.add(element);
			commandsCollector.addPhaseToProject(element, this.parent);
			commandsCollector.save(element);
		}
		setFilteredItems(this.allElements);
	}

	@Override
	public void removeElement(PhaseEntity element) {
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