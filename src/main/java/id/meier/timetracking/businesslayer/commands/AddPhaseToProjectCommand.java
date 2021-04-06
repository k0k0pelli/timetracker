package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.ProjectEntity;

public class AddPhaseToProjectCommand extends BaseCommand {


	private final PhaseEntity phase;
	private final ProjectEntity project;

	public AddPhaseToProjectCommand(PhaseEntity phase, ProjectEntity project) {
		this.phase = phase;
		this.project = project;
	}

	@Override
	public void execute() {
		project.getPhases().add(phase);
	}

    @Override
    public void computeOrder(Context context) {
        this.order =  context.getOrder(this, phase, project);
    }
}
