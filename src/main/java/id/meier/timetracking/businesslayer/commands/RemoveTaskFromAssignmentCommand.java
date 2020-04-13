package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.model.Task;

import java.util.List;

public class RemoveTaskFromAssignmentCommand extends BaseCommand {

	private final Task task;
	
	public RemoveTaskFromAssignmentCommand(Task task) {		
		this.task = task;
	}

	public void execute() {
		List<Assignment> assignments = repoAccessor.findAssignmentByTask(task);
		for (Assignment a : assignments) {			
			a.setTask(null);
			repoAccessor.save(a);
		}
	}

	@Override
	public void computeOrder(Context context) {
		this.order =  context.getOrder(this, task);
	}
}
