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
import java.util.List;

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

    public ProjectsManagementView(ManageProjectStructureController projectStructureController) {

        projectElementEditor = new ProjectElementEditor(projectStructureController);
		projectElementView = new ProjectElementView(projectElementEditor, projectStructureController);

		this.projectStructureController = projectStructureController;
		HorizontalLayout  phaseTaskPanel = createPhaseTaskPanel();
		this.add(projectElementView, phaseTaskPanel);
        createListeners();
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
        TaskElementEditor taskEditor = new TaskElementEditor(projectStructureController);
		taskView = new TaskElementView(projectStructureController,taskEditor);
		hl.add(phaseView, taskView);
		hl.setWidthFull();
		return hl;
	}

	private void createListeners() {
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

    private <T extends DescribedElement, U extends DescribedElement> void setViewData(SubElementView<U, T> view, List<U> data, T parent)  {
        view.setParent(parent);
	    view.setData(data);
	    view.enableAddButton(data != null);
    }
}