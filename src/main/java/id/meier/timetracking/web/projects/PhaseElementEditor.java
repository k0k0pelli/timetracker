package id.meier.timetracking.web.projects;


import id.meier.timetracking.adapter.in.web.ManageProjectStructureController;
import id.meier.timetracking.application.port.in.structuremanagment.commands.RemovePhaseCommand;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SavePhaseCommand;
import id.meier.timetracking.domain.Phase;

public class PhaseElementEditor extends SubElementEditor<Phase> {
	private ManageProjectStructureController projectStructureController;
	PhaseElementEditor(ManageProjectStructureController projectStructureController) {
		super(Phase.class);
		this.projectStructureController = projectStructureController;
	}

	@Override
	protected void saveElement(Phase element) {
		//projectStructureController.savePhase(SavePhaseCommand.of(element));
	}


	@Override
	protected void deleteElement(Phase element) {
		//projectStructureController.removePhase(RemovePhaseCommand.of(element));
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
	protected String getDeleteLabel() {
		return getTranslation("time.tracking.phase.management.delete");
	}

	@Override
	protected String getCancelLabel() {
		return getTranslation("time.tracking.phase.management.cancel");
	}

	@Override
	protected String getSaveLabel() {
		return getTranslation("time.tracking.phase.management.save");
	}

	@Override
	protected String getActiveLabel() {
		return getTranslation("time.tracking.phase.management.active");
	}
}
