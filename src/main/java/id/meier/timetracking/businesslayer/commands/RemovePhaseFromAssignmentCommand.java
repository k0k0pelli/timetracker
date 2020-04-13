package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.model.Phase;

import java.util.List;

public class RemovePhaseFromAssignmentCommand extends BaseCommand {

	private final Phase phase;
	
	public RemovePhaseFromAssignmentCommand(Phase phase) {		
		this.phase = phase;
	}

	public void execute() {
		List<Assignment> assignments = repoAccessor.findAssignmentByPhase(phase);
		for (Assignment a : assignments) {			
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
