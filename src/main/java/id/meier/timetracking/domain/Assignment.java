package id.meier.timetracking.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


public class Assignment implements PersistableElement {
	private Long id;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDate startDate;
	private LocalDate endDate;
	
	private String description;
	
	private Project project;
	
	private Phase phase;

	private Task task;

	private String cloneableTemplateName;

	public Assignment() {		
	}
	
	private Assignment(Long id, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime,
					   String description, String cloneableTemplateName, Project p, Phase phase, Task task) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.project = p;
		this.phase = phase;
		this.task = task;
		this.cloneableTemplateName = cloneableTemplateName;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime start) {
		this.startTime = start;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime end) {
		this.endTime = end;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Phase getPhase() {
		return phase;
	}
	public void setPhase(Phase phase) {
		this.phase = phase;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public void setCloneableTemplateName(String templateName) {
		this.cloneableTemplateName = templateName;
	}

	public Double getIndustrialHoursDiff() {
		long diff = getSecondsDiff();
		return ((double)diff) / 3600d;
	}

	private long getSecondsDiff() {
		long diff = 0;
		if (startDate != null && startTime != null && endDate != null && endTime != null) {
			LocalDateTime start = LocalDateTime.of(startDate, startTime);
			LocalDateTime end = LocalDateTime.of(endDate, endTime);
		 
			diff = ChronoUnit.SECONDS.between(start, end);			
		}
		return diff; 
	}

	public String getProjectName() {
		return (this.project != null)?project.getName():"";
	}
	
	public String getPhaseName() {
		return (this.phase != null)?phase.getName():"";
	}
	
	public String getTaskName() {
		return (this.task != null)?task.getName():"";
	}

	public String getCloneableTemplateName() {
		return this.cloneableTemplateName;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private LocalTime startTime;
		private LocalTime endTime;
		private LocalDate startDate;
		private LocalDate endDate;
		private String description;
		private Project project;
		private Phase phase;
		private Task task;
		private String cloneableTemplateName;

		private Builder() {
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withStartTime(LocalTime time) {
			this.startTime = time;
			return this;
		}

		public Builder withStartTime(LocalDate date) {
			this.startDate = date;
			return this;
		}

		public Builder withEndTime(LocalTime time) {
			this.endTime = time;
			return this;
		}

		public Builder withEndTime(LocalDate date) {
			this.endDate = date;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder withProject(Project project) {
			this.project = project;
			return this;
		}

		public Builder withPhase(Phase phase) {
			this.phase = phase;
			return this;
		}

		public Builder withTask(Task task) {
			this.task = task;
			return this;
		}

		public Builder withcloneTemplateName(String templateName) {
			this.cloneableTemplateName = templateName;
			return this;
		}

		public Assignment build() {
			return  new Assignment(id, startDate, startTime, endDate, endTime, description,
					cloneableTemplateName, project, phase, task);
		}

	}
}
