package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.dblayer.repository.RepositoryAccessor;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Task;

import java.util.List;

public class RemoveTaskFromPhaseCommand extends BaseCommand {

	private final Task task;

	public RemoveTaskFromPhaseCommand(Task task) {
		this.task = task;
	}

	public void execute() {
		List<Phase> projects = repoAccessor.findProjectByPhase(task);
		for (Phase p : projects) {
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
