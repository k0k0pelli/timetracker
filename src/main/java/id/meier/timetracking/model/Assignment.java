package id.meier.timetracking.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Entity
public class Assignment implements PersistableElement {
	@Id
	@GeneratedValue
	private Long id;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDate startDate;
	private LocalDate endDate;
	
	private String description;
	
	@ManyToOne
	private Project project;
	
	@ManyToOne
	private Phase phase;

	@ManyToOne
	private Task task;

	@Column(length = 20)
	private String cloneableTemplateName;

	public Assignment() {		
	}
	
	public Assignment(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, String description, String cloneableTemplateName, Project p, Phase phase, Task task) {
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
}
