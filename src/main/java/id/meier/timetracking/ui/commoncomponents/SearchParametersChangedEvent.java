package id.meier.timetracking.ui.commoncomponents;

import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.ProjectEntity;
import id.meier.timetracking.db.entity.TaskEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public class SearchParametersChangedEvent {
	private final LocalDate startDate;
	private final LocalTime startTime;
	private final LocalDate endDate;
	private final LocalTime endTime;
	private final ProjectEntity project;
	private final PhaseEntity phase;
	private final TaskEntity task;

	SearchParametersChangedEvent(LocalDate startDate, LocalTime startTime,
								 LocalDate endDate, LocalTime endTime, ProjectEntity project, PhaseEntity phase, TaskEntity task) {
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
	public ProjectEntity getProject() {
		return project;
	}
	public PhaseEntity getPhase() {
		return phase;
	}
	public TaskEntity getTask() {
		return task;
	}
}
