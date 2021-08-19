package id.meier.timetracking.application.port.in.assignmentmangement.commands;

import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;
import id.meier.timetracking.domain.Phase;

import java.time.LocalDate;
import java.time.LocalTime;

public class SelectAssignmentCommand {
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private Project project;
    private Phase phase;
    private Task task;
    private Long id;
    private String templateName;

    private SelectAssignmentCommand(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime,
                                    Project project, Phase phase, Task task, Long id, String templateName) {
        this.startDate = startDate;
        this.startTime =startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.project = project;
        this.phase = phase;
        this.task = task;
        this.id = id;
        this.templateName = templateName;
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

    public Long getId() { return id; }

    public String getTemplateName() { return templateName; }

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

        public SelectAssignmentCommand build() {
            if (id != null) {
                String exceptionMessage=null;
                if (startDate != null) exceptionMessage = "startDate";
                if (startTime != null) exceptionMessage = "startTime";
                if (endDate != null) exceptionMessage = "endDate";
                if (endTime != null) exceptionMessage = "startDate";
                if (project != null) exceptionMessage = "endTime";
                if (phase != null) exceptionMessage = "phase";
                if (task != null) exceptionMessage = "task";
                if (templateName != null) exceptionMessage = "templateName";
                if (exceptionMessage != null) {
                    throw new RuntimeException(
                            String.format("id must not be specified as only search criteria! %s must be set to null",
                                    exceptionMessage));
                }
            }
            return new SelectAssignmentCommand(this.startDate, this.startTime, this.endDate, this.endTime, project, phase
            , task, id, templateName);
        }
    }

}
