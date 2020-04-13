package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;

public class AddPhaseToProjectCommand extends BaseCommand {


	private final Phase phase;
	private final Project project;

	public AddPhaseToProjectCommand(Phase phase, Project project) {
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
