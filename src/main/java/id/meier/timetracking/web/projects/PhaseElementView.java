package id.meier.timetracking.web.projects;


import id.meier.timetracking.adapter.in.web.ManageProjectStructureController;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SavePhaseCommand;
import id.meier.timetracking.domain.DescribedElement;
import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;

public class PhaseElementView extends SubElementView<Phase, Project> {
	private ManageProjectStructureController projectStructureController;
	PhaseElementView(SubElementEditor<Phase> editor, ManageProjectStructureController projectStructureController) {
		super(false, Phase.class, editor);
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
		if (!addedElements.contains(element)) {
			this.addedElements.add(element);
			parent.getPhases().add(element);
		}
		setFilteredItems(this.allElements);
	}

	@Override
	public void removeElement(Phase element) {
		if (!removedElements.contains(element)) {
			removedElements.add(element);
			parent.getPhases().remove(element);
		}
		removedElements.add(element);
		setFilteredItems(allElements);
	}

    @Override
    public void onChange(ChangeAction action, Phase element) {

    }



}