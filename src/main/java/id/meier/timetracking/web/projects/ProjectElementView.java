package id.meier.timetracking.web.projects;


import id.meier.timetracking.adapter.in.web.ManageProjectStructureController;
import id.meier.timetracking.application.port.in.structuremanagment.StructureModificationCollector;
import id.meier.timetracking.domain.Project;

public class ProjectElementView extends ElementView<Project> {

	private final ManageProjectStructureController projectStructureController;

	ProjectElementView(ElementEditor<Project> editor,
					   ManageProjectStructureController projectStructureController,
					   StructureModificationCollector modificationCommandsCollector) {
		super(true, Project.class, editor, modificationCommandsCollector);
		editor.addDescribedElementModifiedListener(this);
		this.projectStructureController = projectStructureController;

		refresh();
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
    public void onChange(ChangeAction action, Project element) {
        refresh();
    }

    void refresh() {
		allElements = projectStructureController.getProjects();
		setData(allElements);
	}

	protected  String getGridHeight() {
		return "300px";
	}
}
