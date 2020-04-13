package id.meier.timetracking.dblayer.repository;

import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;

public class AssignmentRefHitParadeProjectPhaseTask {
	private final Project project;
	private final Long projectCount;
	private final Phase phase;
	private final Long phaseCount;
	private final Task task;
	private final Long taskCount;

	public AssignmentRefHitParadeProjectPhaseTask(Project project, Long projectCount, Phase phase, Long phaseCount,
												  Task task, Long taskCount) {
		this.project = project;
		this.projectCount = projectCount;
		this.phase = phase;
		this.phaseCount = phaseCount;		
		this.task = task;
		this.taskCount = taskCount;
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
	
	public Long getProjectCount() {
		return projectCount;
	}

	public Long getPhaseCount() {
		return phaseCount;
	}

	public Long getTaskCount() {
		return taskCount;
	}
}
