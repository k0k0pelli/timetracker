package id.meier.timetracking.ui.projects;


import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.businesslayer.context.ProjectManageRepositoryContext;
import id.meier.timetracking.dblayer.repository.RepositoryAccessor;
import id.meier.timetracking.model.DescribedElement;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Task;
import org.springframework.core.annotation.Order;

import java.util.List;

@SpringComponent
@UIScope
@Order(10)
public class ProjectsManagementView extends VerticalLayout  {
	private final ProjectElementView projectElementView;
    private final ProjectElementEditor projectElementEditor;
    private final CommandsCollector commandsCollector;
	private final RepositoryAccessor repositoryAccessor;

    private PhaseElementView phaseView;
    private TaskElementView taskView;
    private PhaseElementEditor phaseEditor;

    public ProjectsManagementView(RepositoryAccessor repositoryAccessor) {
		commandsCollector = new CommandsCollector(new ProjectManageRepositoryContext());
        projectElementEditor = new ProjectElementEditor(commandsCollector, repositoryAccessor);
		projectElementView = new ProjectElementView(commandsCollector, projectElementEditor, repositoryAccessor);

		this.repositoryAccessor = repositoryAccessor;
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
        phaseEditor = new PhaseElementEditor(commandsCollector, repositoryAccessor);
		phaseView = new PhaseElementView(commandsCollector, phaseEditor);
        TaskElementEditor taskEditor = new TaskElementEditor(commandsCollector, repositoryAccessor);
		taskView = new TaskElementView(commandsCollector, taskEditor);
		hl.add(phaseView, taskView);
		hl.setWidthFull();
		return hl;
	}

	private void createListeners() {
        projectElementView.addListener(l -> {
            List<Phase> data = null;
            if (l != null) {
                data = l.getPhases();
            }
            setViewData(phaseView, data, l);
        });
	    phaseView.addListener(l -> {
            List<Task> data = null;
            if (l != null) {
                data = l.getTasks();
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