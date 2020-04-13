package id.meier.timetracking.ui.projects;

import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.dblayer.repository.RepositoryAccessor;
import id.meier.timetracking.model.Project;

public class ProjectElementEditor extends ElementEditor<Project> {
	private final RepositoryAccessor repoAccessor;
	ProjectElementEditor(CommandsCollector commandsCollector, RepositoryAccessor repoAccessor) {
		super(commandsCollector, Project.class);
		this.repoAccessor = repoAccessor;
	}

	@Override
	protected String getNameLabel() {
		return getTranslation("time.tracking.project.management.name");
	}

	@Override
	protected String getDescriptionLabel() {
		return getTranslation("time.tracking.project.management.description");
	}

	@Override
	protected String getDeleteLabel() {
		return getTranslation("time.tracking.project.management.delete");
	}

	@Override
	protected String getCancelLabel() {
		return getTranslation("time.tracking.project.management.cancel");
	}

	@Override
	protected String getSaveLabel() {
		return getTranslation("time.tracking.project.management.save");
	}

	@Override
	protected String getActiveLabel() {
		return getTranslation("time.tracking.project.management.active");
	}

    @Override
    protected void saveEditedElement(Project element) {
        commandsCollector.save(element);
        commandsCollector.performPersistingOperations(repoAccessor);
		fireElementModified(ElementEditorChangeListener.ChangeAction.SAVE);
    }

    @Override
	protected void deleteEditedElement(Project project) {
		commandsCollector.removeProjectFromAssignment(project);
		commandsCollector.delete(project);
		commandsCollector.performPersistingOperations(repoAccessor);
		fireElementModified(ElementEditorChangeListener.ChangeAction.DELETE);
	}
}
