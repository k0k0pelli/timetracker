package id.meier.timetracking.web.projects;

import id.meier.timetracking.adapter.in.web.ManageProjectStructureController;
import id.meier.timetracking.application.port.in.structuremanagment.StructureModificationCollector;
import id.meier.timetracking.application.port.in.structuremanagment.commands.RemoveCommand;
import id.meier.timetracking.application.port.in.structuremanagment.commands.RemoveProjectCommand;
import id.meier.timetracking.domain.Project;

public class ProjectElementEditor extends ElementEditor<Project> {
	private final ManageProjectStructureController projectStructureController;
	private final StructureModificationCollector structureModificationCollector;
	ProjectElementEditor(ManageProjectStructureController projectStructureController, StructureModificationCollector structureModificationCollector) {
		super(Project.class);
		this.projectStructureController = projectStructureController;
		this.structureModificationCollector = structureModificationCollector;
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
		structureModificationCollector.addElementForSaveIfNotContained(element);

		projectStructureController.saveProjectWithDependentEntities(this.structureModificationCollector);
        fireElementModified(ElementEditorChangeListener.ChangeAction.SAVE, element);
    }

    @Override
	protected void deleteEditedElement(Project project) {
		structureModificationCollector.addElementForRemoveIfNotContained(project);

		projectStructureController.remove(RemoveProjectCommand.of(project));
		fireElementModified(ElementEditorChangeListener.ChangeAction.DELETE, project);
	}
}
