package id.meier.timetracking.ui.projects;

import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.dblayer.repository.RepositoryAccessor;
import id.meier.timetracking.model.Project;

public class ProjectElementView extends ElementView<Project> {

	private final RepositoryAccessor repositoryAccessor;

	ProjectElementView(CommandsCollector commandsCollector, ElementEditor<Project> editor,
					   RepositoryAccessor repositoryAccessor) {
		super(true, commandsCollector, Project.class, editor);
		editor.addDescribedElementModifiedListener(this);
		this.repositoryAccessor = repositoryAccessor;
		onChange(ChangeAction.SAVE);
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
	protected String getSaveLabel() {
		return getTranslation("time.tracking.task.management.save");
	}
	
	@Override
	protected String getAddNewElementLabel() {
		return getTranslation("time.tracking.project.management.create");
	}

    @Override
    public void onChange(ChangeAction action) {
        refresh();
    }

    void refresh() {
		allElements = repositoryAccessor.getProjectRepo().findAll();
		setData(allElements);
	}

	protected  String getGridHeight() {
		return "300px";
	}
}
