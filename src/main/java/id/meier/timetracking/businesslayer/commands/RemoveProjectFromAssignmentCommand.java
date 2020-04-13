package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.model.Project;

import java.util.List;

public class RemoveProjectFromAssignmentCommand extends BaseCommand {

	private final Project project;
	
	public RemoveProjectFromAssignmentCommand(Project project) {		
		this.project = project;
	}

	public void execute() {		
		List<Assignment> assignments = repoAccessor.findAssignmentByProject(project);
		for (Assignment a : assignments) {			
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
