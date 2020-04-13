package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Task;

public class AddTaskToPhaseCommand extends BaseCommand {
	private final Task task;
	private final Phase phase;

	public AddTaskToPhaseCommand(Task task, Phase phase) {
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
