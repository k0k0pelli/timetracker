package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.db.entity.TaskEntity;
import id.meier.timetracking.db.repository.RepositoryAccessor;
import id.meier.timetracking.db.entity.PhaseEntity;

import java.util.List;

public class RemoveTaskFromPhaseCommand extends BaseCommand {

	private final TaskEntity task;

	public RemoveTaskFromPhaseCommand(TaskEntity task) {
		this.task = task;
	}

	public void execute() {
		List<PhaseEntity> projects = repoAccessor.findProjectByPhase(task);
		for (PhaseEntity p : projects) {
			p.getTasks().remove(task);
			repoAccessor.save(p);
		}
	}


	public void setRepoAccessor(RepositoryAccessor repoAccessor) {
		this.repoAccessor = repoAccessor;
	}

	@Override
	public void computeOrder(Context context) {
		this.order =  context.getOrder(this, task);
	}
}
