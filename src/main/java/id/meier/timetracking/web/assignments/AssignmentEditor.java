package id.meier.timetracking.web.assignments;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.GeneratedVaadinComboBox.CustomValueSetEvent;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import id.meier.timetracking.TimeTrackerException;

import id.meier.timetracking.adapter.in.web.ConsistencyCheckController;
import id.meier.timetracking.adapter.in.web.ManageAssignmentController;
import id.meier.timetracking.adapter.in.web.ManageProjectStructureController;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.RemoveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SaveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IConsistencyMessage;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SavePhaseCommand;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SaveProjectCommand;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SaveTaskCommand;
import id.meier.timetracking.domain.*;
import id.meier.timetracking.web.commoncomponents.ComboBoxProvider;
import id.meier.timetracking.web.commoncomponents.ComboBoxProviderResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringComponent
@UIScope
@Order(9)
class AssignmentEditor extends VerticalLayout implements KeyNotifier, IAssignmentEditor {

    private TextField cloneableTemplateName;
    private TextArea description;
    private Assignment assignment;
    private DatePicker startDate;
    private TimePicker startTime;
    private DatePicker endDate;
    private TimePicker endTime;
    private ComboBox<Project> project;
    private ComboBox<Phase> phase;
    private ComboBox<Task> task;

    private final ComboBoxProvider<Project> projectProvider;
    private final ComboBoxProvider<Phase> phaseProvider;
    private final ComboBoxProvider<Task> taskProvider;

    private final Button cancel;
    private boolean newAssignment = false;
	private boolean terminateOldAssignments = false;
    private final AssignmentCreator assignmentCreator;
    
    private final Binder<Assignment> binder = new Binder<>(Assignment.class);
    private ChangeHandler changeHandler;


    private final ManageAssignmentController manageAssignmentController;
    private final ManageProjectStructureController manageProjectStructureController;
    private final ConsistencyCheckController consistencyChecker;
    private boolean valueChangeActive;

	@Autowired
    public AssignmentEditor(AssignmentCreator assignmentCreator, ManageAssignmentController manageAssignmentController,
            ManageProjectStructureController manageProjectStructureController, ConsistencyCheckController consistencyChecker) {
    	this.manageAssignmentController = manageAssignmentController;
    	this.manageProjectStructureController = manageProjectStructureController;
    	this.assignmentCreator = assignmentCreator;
    	this.consistencyChecker = consistencyChecker;

        Button save = new Button(getTranslation("time.tracking.assignment.editor.btn.save"), VaadinIcon.CHECK.create());
    	cancel = new Button(getTranslation("time.tracking.assignment.editor.btn.cancel"));
        Button delete = new Button(getTranslation("time.tracking.assignment.editor.btn.delete"), VaadinIcon.TRASH.create());
        HorizontalLayout actions = new HorizontalLayout(save, delete, cancel);
        Notification n;
        projectProvider = new ComboBoxProvider<>(Project.class, () -> manageProjectStructureController.getProjects());

        phaseProvider = new ComboBoxProvider<>(Phase.class, () ->{
            List<Phase> phases = new ArrayList<>();
            if (project != null && project.getValue() != null && project.getValue().getPhases() != null) {
                phases.addAll(project.getValue().getPhases());
            }
            return phases;
        });
        taskProvider = new ComboBoxProvider<>(Task.class, () -> {
            List<Task> tasks = new ArrayList<>();
            if (phase != null && phase.getValue() != null && phase.getValue().getTasks() != null) {
                tasks.addAll(phase.getValue().getTasks());
            }
            return tasks;
        });

        
        FormLayout timePanel = createDateTimePanel();       	
        FormLayout projectReference = createProjectPhaseTaskPanel();
    	FormLayout descriptionPanel = createDescriptionPanel();
    	add(timePanel, projectReference, descriptionPanel, actions);
    	this.setMargin(false);
    	this.setPadding(true);
    	this.setSpacing(false);
        binder.bindInstanceFields(this);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.of("Control", "Enter"), e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editAssignment(assignment, this.terminateOldAssignments));
        setVisible(false);

        this.getStyle().set("border", "1px solid LightGray");
        valueChangeActive = true;
    }
    
    private FormLayout createDateTimePanel() {
    	startDate = new DatePicker(getTranslation("time.tracking.assignment.editor.field.start.date"));
    	startTime = new TimePicker(getTranslation("time.tracking.assignment.editor.field.start.time"));
        Button buttonStartTimeSet = new Button(getTranslation("time.tracking.assignment.editor.btn.set.start"),
                e -> {
                    startTime.setValue(LocalTime.now());
                    startDate.setValue(LocalDate.now());
                }
        );
    	buttonStartTimeSet.getStyle().set("valign","bottom");
    	endDate = new DatePicker(getTranslation("time.tracking.assignment.editor.field.end.date"));
    	endTime = new TimePicker(getTranslation("time.tracking.assignment.editor.field.end.time"));
        Button buttonEndTimeSet = new Button(getTranslation("time.tracking.assignment.editor.btn.set.end"),
                e -> {
                    endTime.setValue(LocalTime.now());
                    endDate.setValue(LocalDate.now());
                }
        );
    	FormLayout timeDatePanel = new FormLayout(startDate, startTime, buttonStartTimeSet, endDate, endTime, buttonEndTimeSet);
    	timeDatePanel.setResponsiveSteps(
    	        new ResponsiveStep("20em", 1),
    	        new ResponsiveStep("20em", 2),
    	        new ResponsiveStep("12em", 3));
    	
    	return timeDatePanel;
    }
    
    private FormLayout createDescriptionPanel() {
        description = new TextArea(getTranslation("time.tracking.assignment.editor.field.description"));
        cloneableTemplateName = new TextField(getTranslation("time.tracking.assignment.editor.field.cloneable.template.name"));
    	FormLayout projectReference = new FormLayout(description, cloneableTemplateName);
    	projectReference.setResponsiveSteps(
    	        new ResponsiveStep("20em", 1),
               new ResponsiveStep("20em", 2),
                new ResponsiveStep("20em", 3)

                );
        projectReference.setColspan(description, 2);
        projectReference.setColspan(cloneableTemplateName, 1);
    	return projectReference;
    }
    
    @SuppressWarnings("unchecked")
	private FormLayout createProjectPhaseTaskPanel() {    	
        project = new ComboBox<>(getTranslation("time.tracking.assignment.editor.field.project"), this.projectProvider.getAllElements());
        project.setAllowCustomValue(true);
        project.addCustomValueSetListener(e -> {
        	Project p = addNewComboboxValue(e, projectProvider, project);
        	manageProjectStructureController.saveProject(SaveProjectCommand.of(p));
        });
        project.addValueChangeListener(e -> {
            if (valueChangeActive) {
                Project p = e.getValue();
                phase.setValue(null);
                task.setValue(null);
                if (p != null) {
                    phase.setItems(Collections.unmodifiableList(p.getPhases()));
                } else {
                    phase.setItems(Collections.EMPTY_LIST);
                }
            }
        });
        phase = new ComboBox<>(getTranslation("time.tracking.assignment.editor.field.phase"), this.phaseProvider.getAllElements());
        phase.setAllowCustomValue(true);
        phase.addCustomValueSetListener(e -> {
        	Phase p = addNewComboboxValue(e, phaseProvider, phase);
        	this.manageProjectStructureController.savePhase(SavePhaseCommand.of(p));
        });
        phase.addValueChangeListener(e -> {
            if (valueChangeActive) {
                Phase p = e.getValue();
                task.setValue(null);
                if (p != null) {
                    task.setItems(Collections.unmodifiableList(p.getTasks()));
                } else {
                    task.setItems(Collections.EMPTY_LIST);
                }
            }
        });
        task = new ComboBox<>(getTranslation("time.tracking.assignment.editor.field.task"), this.taskProvider.getAllElements());
        task.setAllowCustomValue(true);
        task.addCustomValueSetListener(e -> {
        	Task t = addNewComboboxValue(e, taskProvider, task);
        	this.manageProjectStructureController.saveTask(SaveTaskCommand.of(t));
        });
        
        FormLayout projectPhaseTaskPanel = new FormLayout(project, phase, task);
        projectPhaseTaskPanel.setResponsiveSteps(
    	        new ResponsiveStep("20em", 1),
    	        new ResponsiveStep("20em", 2),
    	        new ResponsiveStep("12em", 3));
        
        return projectPhaseTaskPanel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void delete() {
        manageAssignmentController.removeAssignment(RemoveAssignmentCommand.of(assignment));
        changeHandler.onChange();
        resetProviders();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void save() {
    	if (this.newAssignment) {
    		this.assignmentCreator.terminateOpenAssignmentsOnSaveNewAssignment(assignment, this.terminateOldAssignments);
    	}
        setProjectsPhaseTasks();
        this.manageAssignmentController.saveAssignment(SaveAssignmentCommand.of(assignment));
        changeHandler.onChange();
        resetProviders();
    }

    private void resetProviders() {
        this.projectProvider.clear();
        this.phaseProvider.clear();
        this.taskProvider.clear();
    }

    private void setProjectsPhaseTasks() {
        if (assignment.getProject() != null) {
            List<Phase> phases = this.phaseProvider.getAllElements();
            for (Phase p : phases) {
                if (!assignment.getProject().getPhases().contains(p)) {
                    assignment.getProject().getPhases().add(p);
                    this.manageProjectStructureController.saveProject(SaveProjectCommand.of(assignment.getProject()));
                }
            }
        }
        if (assignment.getPhase() != null) {
            List<Task> tasks = this.taskProvider.getAllElements();
            for (Task t : tasks) {
                if (!assignment.getPhase().getTasks().contains(t)) {
                    assignment.getPhase().getTasks().add(t);
                    this.manageProjectStructureController.savePhase(SavePhaseCommand.of(assignment.getPhase()));
                }
            }
        }
    }

    public interface ChangeHandler {
        void onChange();
    }

    public void editAssignment(Assignment c, boolean terminateOldAssignments) {
    	valueChangeActive = false;

    	this.terminateOldAssignments = terminateOldAssignments;
        if (c == null) {
            setVisible(false);
            resetProviders();
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            assignment = this.manageAssignmentController.selectAssignment(SelectAssignmentCommand.builder().setId(c.getId()).build());
            newAssignment = false;
            checkConsistency(assignment);
        } else {
            assignment = c;
            newAssignment = true;
        }

        cancel.setVisible(persisted);
        binder.setBean(assignment);
        setVisible(true);
        startTime.focus();
        valueChangeActive = true;
    }

    private void checkConsistency(Assignment a) {
        List<IConsistencyMessage> messages = consistencyChecker.checkConsistency(a);
        AssignmentMessageNotifier message = new AssignmentMessageNotifier(messages, this);
    }

    void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
    
    boolean isNewAssignment() {
		return newAssignment;
	}
    
    private <T extends NamedElement> T
               addNewComboboxValue(CustomValueSetEvent<ComboBox<T>> e, ComboBoxProvider<T> provider,
            		   ComboBox<T> comboBox) {
    	try {
    		
    		ComboBoxProviderResult<T> result = provider.checkAddIfNotFoundAndReturnAllElements(e.getDetail());
			if (result.getAddedElement() != null) {
				comboBox.setItems(result.getAllElements());
				comboBox.setValue(result.getAddedElement());
			}
			return result.getAddedElement();
    	} catch (IllegalAccessException | InstantiationException e1) {
			throw new TimeTrackerException(getTranslation("time.tracking.assignment.editor.exception", e.getDetail()), e1);
		}
    }
    
}
