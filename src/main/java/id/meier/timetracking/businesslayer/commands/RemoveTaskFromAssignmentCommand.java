package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.db.entity.AssignmentEntity;
import id.meier.timetracking.db.entity.TaskEntity;

import java.util.List;

public class RemoveTaskFromAssignmentCommand extends BaseCommand {

	private final TaskEntity task;
	
	public RemoveTaskFromAssignmentCommand(TaskEntity task) {
		this.task = task;
	}

	public void execute() {
		List<AssignmentEntity> assignments = repoAccessor.findAssignmentByTask(task);
		for (AssignmentEntity a : assignments) {
			a.setTask(null);
			repoAccessor.save(a);
		}
	}

	@Override
	public void computeOrder(Context context) {
		this.order =  context.getOrder(this, task);
	}
}
