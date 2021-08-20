package id.meier.timetracking.application.port.out.commands;

import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentCommand;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;
import id.meier.timetracking.domain.Phase;

import java.time.LocalDate;
import java.time.LocalTime;

public class SelectAssignmentEntityCommand {
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private Project project;
    private Phase phase;
    private Task task;
    private Long id;
    private String templateName;

    private SelectAssignmentEntityCommand(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime,
                                          Project project, Phase phase, Task task, Long id, String templateName) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.project = project;
        this.phase = phase;
        this.task = task;
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Project getProject() {
        return project;
    }

    public Phase getPhase() {
        return phase;
    }

    public Task getTask() {
        return task;
    }

    public Long getId() {
        return id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private LocalDate startDate;
        private LocalTime startTime;
        private LocalDate endDate;
        private LocalTime endTime;
        private Project project;
        private Phase phase;
        private Task task;
        private Long id;
        private String templateName;

        private Builder() {
        }

        public Builder setAssignmentCommand(SelectAssignmentCommand
                                            selectInCommand) {
            this.setEndDate(selectInCommand.getEndDate())
                    .setEndTime(selectInCommand.getStartTime())
                    .setStartDate(selectInCommand.getStartDate())
                    .setStartTime(selectInCommand.getStartTime())
                    .setProject(selectInCommand.getProject())
                    .setPhase(selectInCommand.getPhase())
                    .setTask(selectInCommand.getTask())
                    .setId(selectInCommand.getId())
                    .setTemplateName(selectInCommand.getTemplateName());
            return this;
        }

        public Builder setStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setStartTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder setEndTime(LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder setProject(Project project) {
            this.project = project;
            return this;
        }

        public Builder setPhase(Phase phase) {
            this.phase = phase;
            return this;
        }

        public Builder setTask(Task task) {
            this.task = task;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setTemplateName(String templateName) {
            this.templateName = templateName;
            return this;
        }

        public SelectAssignmentEntityCommand build() {
            return new SelectAssignmentEntityCommand(this.startDate, this.startTime, this.endDate, this.endTime, project, phase
            , task, id, templateName);
        }
    }

}
