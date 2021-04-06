package id.meier.timetracking.db.repository;

import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.ProjectEntity;
import id.meier.timetracking.db.entity.TaskEntity;

public class AssignmentRefHitParadeProjectPhaseTask {
	private final ProjectEntity project;
	private final Long projectCount;
	private final PhaseEntity phase;
	private final Long phaseCount;
	private final TaskEntity task;
	private final Long taskCount;

	public AssignmentRefHitParadeProjectPhaseTask(ProjectEntity project, Long projectCount, PhaseEntity phase, Long phaseCount,
												  TaskEntity task, Long taskCount) {
		this.project = project;
		this.projectCount = projectCount;
		this.phase = phase;
		this.phaseCount = phaseCount;		
		this.task = task;
		this.taskCount = taskCount;
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
