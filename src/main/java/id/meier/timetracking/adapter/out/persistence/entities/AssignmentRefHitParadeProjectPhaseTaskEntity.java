package id.meier.timetracking.adapter.out.persistence.entities;

import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;

public class AssignmentRefHitParadeProjectPhaseTaskEntity {
	private final ProjectEntity project;
	private final Long projectCount;
	private final PhaseEntity phase;
	private final Long phaseCount;
	private final TaskEntity task;
	private final Long taskCount;

	public AssignmentRefHitParadeProjectPhaseTaskEntity(ProjectEntity project, Long projectCount, PhaseEntity phase, Long phaseCount,
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
