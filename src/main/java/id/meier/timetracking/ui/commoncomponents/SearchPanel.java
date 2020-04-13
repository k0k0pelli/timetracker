package id.meier.timetracking.ui.commoncomponents;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.KeyNotifier;
import id.meier.timetracking.util.LocaleRetriever;
import org.springframework.core.annotation.Order;


import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import id.meier.timetracking.dblayer.repository.PhaseRepository;
import id.meier.timetracking.dblayer.repository.ProjectRepository;
import id.meier.timetracking.dblayer.repository.TaskRepository;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;


@SpringComponent
@UIScope
@Order(9)
public class SearchPanel extends VerticalLayout  implements KeyNotifier {
    
	private final List<SearchParametersChangedListener> changeListener;
	private final DatePicker searchStartDate;
	private final TimePicker searchStartTime;
	private final DatePicker searchEndDate;
    private final TimePicker searchEndTime;
    private final Button buttonSetTimeFilterForToday;
    private ComboBox<Project> projectFilter;
    private ComboBox<Phase> phaseFilter;
    private ComboBox<Task> taskFilter;

	@SuppressWarnings("unchecked")
	public SearchPanel(TaskRepository taskRepo, PhaseRepository phaseRepo, ProjectRepository projectRepo, LocaleRetriever localeRetriever) {
    	this.changeListener = new ArrayList<>();
		ComboBoxProvider<Project> projectProvider = new ComboBoxProvider<>(Project.class, projectRepo::findAll);
		addKeyPressListener(Key.ENTER, e -> performSearch());
		ComboBoxProvider<Phase> phaseProvider = new ComboBoxProvider<>(Phase.class, () -> {
			List<Phase> phases = new ArrayList<>();
			if (projectFilter != null && projectFilter.getValue() != null && projectFilter.getValue().getPhases() != null) {
				phases.addAll(projectFilter.getValue().getPhases());
			}
			return phases;
		});
		ComboBoxProvider<Task> taskProvider = new ComboBoxProvider<>(Task.class, () -> {
			List<Task> tasks = new ArrayList<>();
			if (phaseFilter != null && phaseFilter.getValue() != null && phaseFilter.getValue().getTasks() != null) {
				tasks.addAll(phaseFilter.getValue().getTasks());
			}
			return tasks;
		});
        
    	searchStartDate = new DatePicker(getTranslation("time.tracking.search.panel.from.date"));
    	searchStartDate.setLocale(localeRetriever.getLocale());
    	searchStartTime = new TimePicker(getTranslation("time.tracking.search.panel.from.time"));
    	searchStartTime.setLocale(localeRetriever.getLocale());
    	searchEndDate = new DatePicker(getTranslation("time.tracking.search.panel.to.date"));
    	searchEndDate.setLocale(localeRetriever.getLocale());
    	searchEndTime = new TimePicker(getTranslation("time.tracking.search.panel.to.time"));
    	searchEndTime.setLocale(localeRetriever.getLocale());
    	
    	buttonSetTimeFilterForToday = new Button(getTranslation("time.tracking.search.panel.btn.set.filter.for.today"),
				this::onComponentEvent2
    	);
    	
    	projectFilter = new ComboBox<>(getTranslation("time.tracking.search.panel.project"), projectProvider.getAllElements());
        projectFilter.addValueChangeListener(e -> {
        	Project p = e.getValue();
        	phaseFilter.setValue(null);
    		taskFilter.setValue(null);
        	if (p != null) {
        		phaseFilter.setItems(p.getPhases());
        		//taskFilter.setItems(p.getTasks());
        	} else {
        		phaseFilter.setItems(Collections.EMPTY_LIST);        		
        		taskFilter.setItems(Collections.EMPTY_LIST);
        	}
        });
    	phaseFilter = new ComboBox<>(getTranslation("time.tracking.search.panel.phase"), phaseProvider.getAllElements());
        taskFilter = new ComboBox<>(getTranslation("time.tracking.search.panel.task"), taskProvider.getAllElements());
		Button performSearch = new Button(getTranslation("time.tracking.search.panel.btn"),
				this::onComponentEvent
		);
        performSearch.addClickShortcut(Key.ENTER);
        FormLayout hl = new FormLayout();
        hl.add(searchStartDate, searchStartTime, searchEndDate, searchEndTime, 
        		buttonSetTimeFilterForToday, projectFilter, phaseFilter, taskFilter, performSearch);
        hl.setResponsiveSteps(
    	        new ResponsiveStep("20em", 1),
    	        new ResponsiveStep("20em", 2),
    	        new ResponsiveStep("20em", 3),
    	        new ResponsiveStep("20em", 4),
    	        new ResponsiveStep("20em", 5),
    	        new ResponsiveStep("20em", 6),
    	        new ResponsiveStep("20em", 7),
    	        new ResponsiveStep("20em", 8),
    	        new ResponsiveStep("20em", 9)
    	        );
        this.add(hl);
        this.getStyle().set("border", "1px solid LightGray");
        this.setAlignItems(Alignment.BASELINE);
        this.setMargin(false);
    	this.setPadding(true);
    	this.setSpacing(false);
        
	}
    
    private void setStartVisibility(boolean visible) {
    	this.searchStartDate.setVisible(visible);
    	this.searchStartTime.setVisible(visible);    	
    }
    
    private void setEndVisibility(boolean visible) {
    	this.searchEndDate.setVisible(visible);
    	this.searchEndTime.setVisible(visible);    	
    }
    
    private void setTodayFilterButtonVisible(boolean visible) {
    	this.buttonSetTimeFilterForToday.setVisible(visible);   	
    }
    
    public void setDateFilterVisible(boolean visible) {
    	this.setStartVisibility(visible);
    	this.setEndVisibility(visible);
    	this.setTodayFilterButtonVisible(visible);
    	
    }
    
    public void setProjectVisibility(boolean visible) {
    	this.projectFilter.setVisible(visible);
    }
    
    public void setPhaseVisibility(boolean visible) {
    	this.phaseFilter.setVisible(visible);
    }
    
    public void setTasVisibility(boolean visible) {
    	this.taskFilter.setVisible(visible);
    }
    
    public void setTodayFilter() {
    	searchStartTime.setValue(LocalTime.of(0, 0));
		searchStartDate.setValue(LocalDate.now());		
    }
	
    public void addChangeListener(SearchParametersChangedListener listener) {
    	this.changeListener.add(listener);    	
    }
    
    public void removeChangeListener(SearchParametersChangedListener listener) {
    	this.changeListener.remove(listener);    	
    }
    
    private LocalDate getSearchStartDate() {
		return searchStartDate.getValue();
	}

	private LocalTime getSearchStartTime() {
		return searchStartTime.getValue();
	}

	private LocalDate getSearchEndDate() {
		return searchEndDate.getValue();
	}

	private LocalTime getSearchEndTime() {
		return searchEndTime.getValue();
	}

	private Project getProjectFilter() {
		return projectFilter.getValue();
	}

	private Phase getPhaseFilter() {
		return phaseFilter.getValue();
	}

	private Task getTaskFilter() {
		return taskFilter.getValue();
	}
	
	public void performSearch() {
		SearchParametersChangedEvent event = new SearchParametersChangedEvent(getSearchStartDate(), getSearchStartTime(), 
				getSearchEndDate(), getSearchEndTime(), getProjectFilter(), getPhaseFilter(), getTaskFilter());
		for (SearchParametersChangedListener e : this.changeListener) {
			e.searchParametersChanged(event);
		}
	}


	private void onComponentEvent(ClickEvent<Button> e) {
		performSearch();
	}

	private void onComponentEvent2(ClickEvent<Button> e) {
		setTodayFilter();
	}
}
