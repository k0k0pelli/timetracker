package id.meier.timetracking.application.port.out.commands;

import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;

public class RemoveTaskEntityCommand {
	private final Task task;

	private RemoveTaskEntityCommand(Task task) {
		this.task = task;
	}

	public Task getTask() {
		return task;
	}

	public static RemoveTaskEntityCommand of(Task task) {
		return new RemoveTaskEntityCommand(task);
	}
}
