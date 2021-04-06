package id.meier.timetracking.ui.projects;

import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.db.repository.RepositoryAccessor;
import id.meier.timetracking.db.entity.PhaseEntity;

public class PhaseElementEditor extends SubElementEditor<PhaseEntity> {
	PhaseElementEditor(CommandsCollector commandsCollector, RepositoryAccessor repoAccessor) {
		super(commandsCollector, PhaseEntity.class);
	}

	@Override
	protected void deleteEditedElement(PhaseEntity phase) {
		commandsCollector.removePhaseFromAssignment(phase);
		commandsCollector.removePhaseFromProject(phase);
		super.deleteEditedElement(phase);
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
