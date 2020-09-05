package id.meier.timetracking;

import com.vaadin.flow.spring.annotation.UIScope;
import id.meier.timetracking.dblayer.repository.PhaseRepository;
import id.meier.timetracking.dblayer.repository.ProjectRepository;
import id.meier.timetracking.dblayer.repository.TaskRepository;
import id.meier.timetracking.ui.commoncomponents.AssignmentOverviewPanel;
import id.meier.timetracking.ui.commoncomponents.SearchPanel;
import id.meier.timetracking.util.AssignmentExporter;
import id.meier.timetracking.util.LocaleRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfig {
	
	@Bean
	@UIScope 
	public AssignmentOverviewPanel assignmentViewAssignmentOverviewPanel(AssignmentExporter assignmentExporter) {
		return new AssignmentOverviewPanel(assignmentExporter);
	}
	
	@Bean
	@UIScope 
	public AssignmentOverviewPanel reportingViewAssignmentOverviewPanel(AssignmentExporter assignmentExporter) {
		return new AssignmentOverviewPanel(assignmentExporter);
	}
	
	@Bean
	@UIScope
	public SearchPanel reportingSearchPanel(TaskRepository taskRepo, PhaseRepository phaseRepo, ProjectRepository projectRepo, LocaleRetriever localeRetriever) {
		return new SearchPanel(taskRepo, phaseRepo, projectRepo, localeRetriever);
	}
	
	@Bean
	@UIScope
	public SearchPanel assignmentManagementSearchPanel(TaskRepository taskRepo, PhaseRepository phaseRepo, ProjectRepository projectRepo, LocaleRetriever localeRetriever) {
		SearchPanel searchPanel = new SearchPanel(taskRepo, phaseRepo, projectRepo, localeRetriever);
		searchPanel.setTodayFilter();
		return searchPanel;
	}
	

	
}