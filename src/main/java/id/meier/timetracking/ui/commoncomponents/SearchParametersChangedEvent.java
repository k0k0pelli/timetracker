package id.meier.timetracking.ui.commoncomponents;

import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;

import java.time.LocalDate;
import java.time.LocalTime;

public class SearchParametersChangedEvent {
	private final LocalDate startDate;
	private final LocalTime startTime;
	private final LocalDate endDate;
	private final LocalTime endTime;
	private final Project project;
	private final Phase phase;
	private final Task task;

	SearchParametersChangedEvent(LocalDate startDate, LocalTime startTime,
								 LocalDate endDate, LocalTime endTime, Project project, Phase phase, Task task) {
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.project = project;
		this.phase = phase;
		this.task = task;
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
}
