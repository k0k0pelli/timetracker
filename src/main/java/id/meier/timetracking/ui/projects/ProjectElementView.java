package id.meier.timetracking.ui.projects;

import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.db.entity.ProjectEntity;
import id.meier.timetracking.db.repository.RepositoryAccessor;

public class ProjectElementView extends ElementView<ProjectEntity> {

	private final RepositoryAccessor repositoryAccessor;

	ProjectElementView(CommandsCollector commandsCollector, ElementEditor<ProjectEntity> editor,
					   RepositoryAccessor repositoryAccessor) {
		super(true, commandsCollector, ProjectEntity.class, editor);
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
