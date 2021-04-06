package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.db.entity.AssignmentEntity;
import id.meier.timetracking.db.entity.ProjectEntity;

import java.util.List;

public class RemoveProjectFromAssignmentCommand extends BaseCommand {

	private final ProjectEntity project;
	
	public RemoveProjectFromAssignmentCommand(ProjectEntity project) {
		this.project = project;
	}

	public void execute() {		
		List<AssignmentEntity> assignments = repoAccessor.findAssignmentByProject(project);
		for (AssignmentEntity a : assignments) {
			a.setTask(null);
			a.setPhase(null);
			a.setProject(null);
			repoAccessor.save(a);
		}
	}

	@Override
	public void computeOrder(Context context) {
		this.order =  context.getOrder(this, project);
	}
}
