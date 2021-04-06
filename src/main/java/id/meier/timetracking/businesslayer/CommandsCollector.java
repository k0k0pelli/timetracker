package id.meier.timetracking.businesslayer;

import id.meier.timetracking.businesslayer.commands.*;
import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.ProjectEntity;
import id.meier.timetracking.db.entity.TaskEntity;
import id.meier.timetracking.db.repository.RepositoryAccessor;
import id.meier.timetracking.db.dto.PersistableElement;

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

	public void addPhaseToProject(PhaseEntity phase, ProjectEntity project) {
		addCommand(new AddPhaseToProjectCommand(phase, project));
	}

	public void addTaskToPhase(TaskEntity task, PhaseEntity phase) {
		addCommand(new AddTaskToPhaseCommand(task, phase));
	}

	public void removeTaskFromAssignment(TaskEntity task) {
		addCommand(new RemoveTaskFromAssignmentCommand(task));
	}
	
	public void removePhaseFromAssignment(PhaseEntity phase) {
		addCommand(new RemovePhaseFromAssignmentCommand(phase));
	}

	public void removeTaskFromProject(TaskEntity task) {
		addCommand(new RemoveTaskFromPhaseCommand(task));
	}

	public void removePhaseFromProject(PhaseEntity phase) {
		addCommand(new RemovePhaseFromProjectCommand(phase));
	}

	public void removeProjectFromAssignment(ProjectEntity project) {
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
