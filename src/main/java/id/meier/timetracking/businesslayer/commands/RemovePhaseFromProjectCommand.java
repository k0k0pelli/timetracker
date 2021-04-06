package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.ProjectEntity;

import java.util.List;

public class RemovePhaseFromProjectCommand extends BaseCommand {

	private final PhaseEntity phase;

	public RemovePhaseFromProjectCommand(PhaseEntity phase) {
		this.phase = phase;
	}

	public void execute() {
		List<ProjectEntity> projects = repoAccessor.findProjectByPhase(phase);
		for (ProjectEntity p : projects) {
			p.getPhases().remove(phase);
			repoAccessor.save(p);
		}
	}

    @Override
    public void computeOrder(Context context) {
        this.order =  context.getOrder(this, phase);
    }

}
