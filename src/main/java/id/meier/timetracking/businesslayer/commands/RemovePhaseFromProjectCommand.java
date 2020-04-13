package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;

import java.util.List;

public class RemovePhaseFromProjectCommand extends BaseCommand {

	private final Phase phase;

	public RemovePhaseFromProjectCommand(Phase phase) {
		this.phase = phase;
	}

	public void execute() {
		List<Project> projects = repoAccessor.findProjectByPhase(phase);
		for (Project p : projects) {
			p.getPhases().remove(phase);
			repoAccessor.save(p);
		}
	}

    @Override
    public void computeOrder(Context context) {
        this.order =  context.getOrder(this, phase);
    }

}
