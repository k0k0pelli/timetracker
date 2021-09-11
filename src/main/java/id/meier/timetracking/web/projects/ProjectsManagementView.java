package id.meier.timetracking.web.projects;


import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import id.meier.timetracking.adapter.in.web.ManageProjectStructureController;
import id.meier.timetracking.domain.DescribedElement;
import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Task;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringComponent
@UIScope
@Order(10)
public class ProjectsManagementView extends VerticalLayout  {
	private final ProjectElementView projectElementView;
    private final ProjectElementEditor projectElementEditor;
    private final ManageProjectStructureController projectStructureController;

    private PhaseElementView phaseView;
    private TaskElementView taskView;
    private PhaseElementEditor phaseEditor;
    private TaskElementEditor taskEditor;
    private Map<Phase, ElementEditorChangeListener.ChangeAction> phaseChanges;
    private Map<Task, ElementEditorChangeListener.ChangeAction> taskChanges;


    public ProjectsManagementView(ManageProjectStructureController projectStructureController) {
        phaseChanges = new HashMap<>();
        taskChanges = new HashMap<>();
        projectElementEditor = new ProjectElementEditor(projectStructureController);
		projectElementView = new ProjectElementView(projectElementEditor, projectStructureController);

		this.projectStructureController = projectStructureController;
		HorizontalLayout  phaseTaskPanel = createPhaseTaskPanel();
		this.add(projectElementView, phaseTaskPanel);
        registerSelectionChangeListeners();
        registerPersistenceChangeListener();
	}

	@Override
	public void setVisible(boolean visible) {
	    super.setVisible(visible);
	    if (visible) {
	        projectElementView.refresh();
        }
    }

	private HorizontalLayout createPhaseTaskPanel() {
		HorizontalLayout hl = new HorizontalLayout();
        phaseEditor = new PhaseElementEditor(projectStructureController);
		phaseView = new PhaseElementView(phaseEditor, projectStructureController);
        taskEditor = new TaskElementEditor(projectStructureController);
		taskView = new TaskElementView(projectStructureController,taskEditor);
		hl.add(phaseView, taskView);
		hl.setWidthFull();
		return hl;
	}

	private void registerSelectionChangeListeners() {
        projectElementView.addListener(l -> {
            List<Phase> data = null;
            if (l != null) {
                data = new ArrayList<>(l.getPhases());
            }
            setViewData(phaseView, data, l);
        });

	    phaseView.addListener(l -> {
            List<Task> data = null;
            if (l != null) {
                data = new ArrayList<>(l.getTasks());
            }
            setViewData(taskView, data, l);
        });
    }

    private void registerPersistenceChangeListener() {
        taskEditor.addDescribedElementModifiedListener((e,d) -> {
            if (e != ElementEditorChangeListener.ChangeAction.CANCEL) {
                this.taskChanges.put(d,e);
            }
        });
        phaseEditor.addDescribedElementModifiedListener((e,d) -> {
            if (e != ElementEditorChangeListener.ChangeAction.CANCEL) {
                this.phaseChanges.put(d,e);
            }
        });
        projectElementEditor.addDescribedElementModifiedListener((e,d) -> {
            if (e != ElementEditorChangeListener.ChangeAction.CANCEL) {
                taskChanges.clear();
                phaseChanges.clear();
            } else {

            }
        });
    }

    private <T extends DescribedElement, U extends DescribedElement> void setViewData(SubElementView<U, T> view, List<U> data, T parent)  {
        view.setParent(parent);
	    view.setData(data);
	    view.enableAddButton(data != null);
    }
}