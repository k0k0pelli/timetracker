package id.meier.timetracking;

import com.vaadin.flow.spring.annotation.UIScope;

import id.meier.timetracking.adapter.in.web.ManageProjectStructureController;
import id.meier.timetracking.application.services.AssignmentExporter;
import id.meier.timetracking.util.LocaleRetriever;
import id.meier.timetracking.web.commoncomponents.AssignmentOverviewPanel;
import id.meier.timetracking.web.commoncomponents.SearchPanel;
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
	public SearchPanel reportingSearchPanel(ManageProjectStructureController manageProjectStructureController, LocaleRetriever localeRetriever) {
		return new SearchPanel(manageProjectStructureController, localeRetriever);
	}
	
	@Bean
	@UIScope
	public SearchPanel assignmentManagementSearchPanel(ManageProjectStructureController manageProjectStructureController, LocaleRetriever localeRetriever) {
		SearchPanel searchPanel = new SearchPanel(manageProjectStructureController, localeRetriever);
		searchPanel.setTodayFilter();
		return searchPanel;
	}
	

	
}