package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.TaskEntity;

public class AddTaskToPhaseCommand extends BaseCommand {
	private final TaskEntity task;
	private final PhaseEntity phase;

	public AddTaskToPhaseCommand(TaskEntity task, PhaseEntity phase) {
		this.task = task; this.phase = phase;
	}

	public void execute() {
		phase.getTasks().add(task);
	}

	@Override
	public void computeOrder(Context context) {
		this.order =  context.getOrder(this, task, phase);
	}
}
