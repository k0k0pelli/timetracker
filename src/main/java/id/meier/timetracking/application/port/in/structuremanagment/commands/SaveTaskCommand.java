package id.meier.timetracking.application.port.in.structuremanagment.commands;

import id.meier.timetracking.domain.Task;

public class SaveTaskCommand {
    private Task task;
    private SaveTaskCommand(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return this.task;
    }

    public static SaveTaskCommand of(Task task) {
        return new SaveTaskCommand(task);
    }
}
