package id.meier.timetracking.application.port.out.commands;

import id.meier.timetracking.domain.Task;

public class SaveTaskEntityCommand {
	private final Task task;

	private SaveTaskEntityCommand(Task task) {
		this.task = task;
	}

	public Task getTask() {
		return task;
	}

	public static SaveTaskEntityCommand of(Task task) {
		return new SaveTaskEntityCommand(task);
	}

}
