package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.db.entity.AssignmentEntity;
import id.meier.timetracking.db.entity.PhaseEntity;

import java.util.List;

public class RemovePhaseFromAssignmentCommand extends BaseCommand {

	private final PhaseEntity phase;
	
	public RemovePhaseFromAssignmentCommand(PhaseEntity phase) {
		this.phase = phase;
	}

	public void execute() {
		List<AssignmentEntity> assignments = repoAccessor.findAssignmentByPhase(phase);
		for (AssignmentEntity a : assignments) {
			a.setTask(null);
			a.setPhase(null);
			repoAccessor.save(a);
		}
	}

	@Override
	public void computeOrder(Context context) {
		this.order =  context.getOrder(this, phase);
	}


}
