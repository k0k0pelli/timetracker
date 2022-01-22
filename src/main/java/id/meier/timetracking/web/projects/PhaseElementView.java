package id.meier.timetracking.web.projects;


import id.meier.timetracking.adapter.in.web.ManageProjectStructureController;
import id.meier.timetracking.application.port.in.structuremanagment.StructureModificationCollector;
import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;

public class PhaseElementView extends SubElementView<Phase, Project> {
	private ManageProjectStructureController projectStructureController;
	PhaseElementView(SubElementEditor<Phase> editor, ManageProjectStructureController projectStructureController, StructureModificationCollector modificationCommandsCollector) {
		super(false, Phase.class, editor, modificationCommandsCollector);
		this.projectStructureController = projectStructureController;
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
		this.getModificationCommandsCollector().addElementForSaveIfNotContained(element);
		setFilteredItems(this.allElements);
	}

	@Override
	public void removeElement(Phase element) {
		this.getModificationCommandsCollector().addElementForRemoveIfNotContained(element);
		setFilteredItems(allElements);
	}

    @Override
    public void onChange(ChangeAction action, Phase element) {

    }



}