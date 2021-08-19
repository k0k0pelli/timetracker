package id.meier.timetracking.application.port.in.structuremanagment.commands;

import id.meier.timetracking.domain.Task;

public class RemoveTaskCommand {
    private Task task;
    private RemoveTaskCommand(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return this.task;
    }

    public static RemoveTaskCommand of(Task task) {
        return new RemoveTaskCommand(task);
    }
}
