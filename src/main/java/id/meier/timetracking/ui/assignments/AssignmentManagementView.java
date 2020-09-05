package id.meier.timetracking.ui.assignments;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import id.meier.timetracking.dblayer.repository.RepositoryAccessor;
import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.ui.commoncomponents.AssignmentOverviewPanel;
import id.meier.timetracking.ui.commoncomponents.SearchPanel;
import id.meier.timetracking.ui.commoncomponents.SearchParametersChangedEvent;
import id.meier.timetracking.ui.commoncomponents.SearchParametersChangedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;

import java.util.List;


@SpringComponent
@UIScope
@Order(10)
public class AssignmentManagementView extends VerticalLayout implements SearchParametersChangedListener {


    private final AssignmentEditor editor;
    private List<Assignment> assignments;
    private final AssignmentOverviewPanel assignmentOverviewPanel;
	private Checkbox terminateNonFinishedAssignments;
    private final AssignmentCreator assignmentCreator;
    private final RepositoryAccessor repositoryAccessor;
	private RadioButtonGroup<String> assignmentCreationOption;
    private TextField templateName;

    @Autowired
    public AssignmentManagementView(
    		AssignmentEditor editor, 
    		@Qualifier("assignmentManagementSearchPanel") SearchPanel searchPanel, 
    		@Qualifier("assignmentViewAssignmentOverviewPanel") AssignmentOverviewPanel assignmentOverviewPanel,
			RepositoryAccessor repositoryAccessor,
    		AssignmentCreator assignmentCreator) {
        this.repositoryAccessor = repositoryAccessor;

        this.assignmentCreator = assignmentCreator;
        searchPanel.addChangeListener(this);
        searchPanel.setVisible(true);
        this.editor = editor;
        this.assignmentOverviewPanel = assignmentOverviewPanel;
        HorizontalLayout actionPanel = createActionPanel(); 
        
        assignmentOverviewPanel.setValueChangeListener(
        		e -> editor.editAssignment(e.getValue(), this.terminateNonFinishedAssignments.getValue())
		);
        
        add(actionPanel, searchPanel, assignmentOverviewPanel, editor);
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            searchPanel.performSearch();
            if (editor.isNewAssignment()) {
            	if (this.assignments.size() > 0) {
            		assignmentOverviewPanel.scrollToItem(assignments.size()-1);
            	}
            }
        });        
        searchPanel.performSearch();
        
    }

	private HorizontalLayout createActionPanel() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setAlignItems(Alignment.BASELINE);
		Button addNewBtn = new Button(getTranslation("time.tracking.assignment.new.btn"), VaadinIcon.PLUS.create());

		templateName = new TextField();
		templateName.setPlaceholder(getTranslation("time.tracking.assignment.new.template.name"));

		assignmentCreationOption = new RadioButtonGroup<>();
		final String guessOption = getTranslation("time.tracking.assignment.new.guess.opt");
		final String templateOptions = getTranslation("time.tracking.assignment.new.create.by.template");
		assignmentCreationOption.setItems(guessOption,templateOptions);
		assignmentCreationOption.setValue(templateOptions);
		assignmentCreationOption.addValueChangeListener( e ->  {
            templateName.setEnabled(e.getValue().equals(templateOptions));
		});

		terminateNonFinishedAssignments = new Checkbox(getTranslation("time.tracking.assignment.new.terminate.opt"));
		terminateNonFinishedAssignments.setValue(true);
		addNewBtn.addClickListener(
				e -> editor.editAssignment(
						assignmentCreator.createNewAssignment(
									assignmentCreationOption.getValue().equals(guessOption),
									templateName.getValue()
								),
								terminateNonFinishedAssignments.getValue()
						)
				);
		layout.add(assignmentCreationOption, templateName, terminateNonFinishedAssignments, addNewBtn);
		return layout;
	}
   
	@Override
	public void searchParametersChanged(SearchParametersChangedEvent event) {
		assignments = this.repositoryAccessor.selectAssignments(event.getStartDate(), event.getStartTime(),
				event.getEndDate(), event.getEndTime(), event.getProject(), event.getPhase(), event.getTask());
		
		assignmentOverviewPanel.setItems(assignments);				
	}

}
