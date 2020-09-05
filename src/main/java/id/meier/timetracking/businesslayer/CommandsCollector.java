package id.meier.timetracking.businesslayer;

import id.meier.timetracking.businesslayer.commands.*;
import id.meier.timetracking.dblayer.repository.RepositoryAccessor;
import id.meier.timetracking.model.PersistableElement;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CommandsCollector {
	private final List<Command> commands;
	private final Context context;

	public CommandsCollector() {
		this(null);
	}

	public CommandsCollector(Context collectorContext) {
		commands = new ArrayList<>();
	    this.context = collectorContext;
    }

	public <T extends PersistableElement> void save(T element) {
		if (element != null) {
			addCommand(new SaveCommand<>(element));
		}
	}
	
	public <T extends PersistableElement> void delete(T element) {
		if (element != null) {
			addCommand(new DeleteCommand<>(element));
		}
	}

	public void addPhaseToProject(Phase phase, Project project) {
		addCommand(new AddPhaseToProjectCommand(phase, project));
	}

	public void addTaskToPhase(Task task, Phase phase) {
		addCommand(new AddTaskToPhaseCommand(task, phase));
	}

	public void removeTaskFromAssignment(Task task) {
		addCommand(new RemoveTaskFromAssignmentCommand(task));
	}
	
	public void removePhaseFromAssignment(Phase phase) {
		addCommand(new RemovePhaseFromAssignmentCommand(phase));
	}

	public void removeTaskFromProject(Task task) {
		addCommand(new RemoveTaskFromPhaseCommand(task));
	}

	public void removePhaseFromProject(Phase phase) {
		addCommand(new RemovePhaseFromProjectCommand(phase));
	}

	public void removeProjectFromAssignment(Project project) {
		addCommand(new RemoveProjectFromAssignmentCommand(project));
	}
	
	public void performPersistingOperations(RepositoryAccessor repoAccessor) {
		List<Command> orderedCommandList;
	    if (this.context != null) {
	        for (Command c :commands) {
                c.computeOrder(context);
            }
			orderedCommandList = new ArrayList<>(commands);
	        orderedCommandList.sort(Comparator.comparingInt(Command::getOrder));
        } else {
	        orderedCommandList = commands;
        }

		repoAccessor.executeCommands(orderedCommandList);
		reset();
	}

	public void reset() {
		this.commands.clear();
	}
	
	private void addCommand(Command command) {
		commands.add(command);
	}

}
