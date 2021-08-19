package id.meier.timetracking.domain;

public class AssignmentRefHitParadeProjectPhaseTask {
	private final Project project;
	private final Long projectCount;
	private final Phase phase;
	private final Long phaseCount;
	private final Task task;
	private final Long taskCount;

	private AssignmentRefHitParadeProjectPhaseTask(Project project, Long projectCount, Phase phase, Long phaseCount,
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

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Project project;
		private Long projectCount;
		private Phase phase;
		private Long phaseCount;
		private Task task;
		private Long taskCount;

		private Builder() {

		}

		public Builder withProject(Project project) {
			this.project = project;
			return this;
		}

		public Builder withProjectCount(Long count) {
			this.projectCount = count;
			return this;
		}

		public Builder withPhase(Phase phase) {
			this.phase = phase;
			return this;
		}

		public Builder withPhaseCount(Long count) {
			this.phaseCount = count;
			return this;
		}

		public Builder withTask(Task task) {
			this.task = task;
			return this;
		}

		public Builder withTaskCount(Long count) {
			this.taskCount = count;
			return this;
		}

		public AssignmentRefHitParadeProjectPhaseTask build() {
			return  new AssignmentRefHitParadeProjectPhaseTask(project, projectCount, phase, phaseCount, task, taskCount);
		}

	}
}
