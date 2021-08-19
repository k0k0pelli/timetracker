package id.meier.timetracking.web.reporting;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import id.meier.timetracking.TimeTrackerException;

import id.meier.timetracking.adapter.in.web.ManageAssignmentController;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentCommand;

import id.meier.timetracking.application.services.ReportGeneratorService;
import id.meier.timetracking.domain.Assignment;
import id.meier.timetracking.web.commoncomponents.AssignmentOverviewPanel;
import id.meier.timetracking.web.commoncomponents.SearchPanel;
import id.meier.timetracking.web.commoncomponents.SearchParametersChangedEvent;
import id.meier.timetracking.web.commoncomponents.SearchParametersChangedListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
@UIScope
@Order(9)
public class ReportingManagementView extends VerticalLayout implements SearchParametersChangedListener {
	private final ReportGeneratorService reportGenerator;
	private final AssignmentOverviewPanel assignmentOverviewPanel;
	private final ManageAssignmentController managementAssignmentController;
	private List<Assignment> assignments;
	private final ReportDefinitionFactory reportingFactory;
	private ComboBox<ReportDefinition> reportSelection;
	
	public ReportingManagementView(ReportGeneratorService reportGenerator,
								   @Qualifier("reportingSearchPanel") SearchPanel searchPanel,
								   @Qualifier("reportingViewAssignmentOverviewPanel") AssignmentOverviewPanel overviewPanel,
								   ManageAssignmentController managementAssignmentController,
								   ReportDefinitionFactory factory) {
		assignments = new ArrayList<>();
		this.reportGenerator = reportGenerator;
		this.assignmentOverviewPanel = overviewPanel;
        this.managementAssignmentController = managementAssignmentController;
        this.reportingFactory = factory;

        HorizontalLayout reportingPanel = createReportingPanel();
        searchPanel.addChangeListener(this);
        searchPanel.setVisible(true);
        
        add(searchPanel, assignmentOverviewPanel, reportingPanel);
        searchPanel.performSearch();
	}
	
	@Override
	public void searchParametersChanged(SearchParametersChangedEvent event) {
		assignments = managementAssignmentController.selectAssignments(SelectAssignmentCommand.builder()
						.setStartDate(event.getStartDate())
						.setStartTime(event.getStartTime())
						.setEndDate(event.getEndDate())
						.setEndTime(event.getEndTime())
						.setProject(event.getProject())
						.setPhase(event.getPhase())
						.setTask(event.getTask())
						.build());

				//.selectAssignments(event.getStartDate(), event.getStartTime(),
				//event.getEndDate(), event.getEndTime(), event.getProject(), event.getPhase(), event.getTask());
		
		assignmentOverviewPanel.setItems(assignments);				
	}
	
	private HorizontalLayout createReportingPanel() {
		HorizontalLayout result = new HorizontalLayout();

		reportSelection = createReportSelection();
		Label groupByLabel = new Label(getTranslation("time.tracking.reporting.report.selection.label"));
		Button button = createDownloadButton();
		
		result.add(groupByLabel);
		result.add(reportSelection);
		result.add(button);
		
		result.setAlignItems(Alignment.BASELINE);

		return result;
	}

	private ComboBox<ReportDefinition> createReportSelection() {
		reportSelection = new ComboBox<>();
		List<ReportDefinition> list = reportingFactory.getReportDefinitions();
		reportSelection.setItems(list);
		reportSelection.setValue(list.get(0));
		return reportSelection;
	}

	private Button createDownloadButton() {
		return new Button("Show Report", VaadinIcon.ARROW_CIRCLE_DOWN.create(), event -> {
			final StreamResource resource = new StreamResource("report.pdf",
					this::generateAndDownloadReport);
			//resource.setContentType("application/pdf");
			final StreamRegistration registration = VaadinSession.getCurrent().getResourceRegistry().registerResource(resource);

			UI.getCurrent().getPage().executeJs("window.open(\"" + registration.getResourceUri().toString() + "\", \"_blank\");" );
		});
	}

	private InputStream generateAndDownloadReport() {
		File outputFile;
		try {
			outputFile = File.createTempFile("TimeTrackerReport", null);
		} catch (Exception ex) {
			throw new TimeTrackerException(ex.getMessage(), ex);
		}
		outputFile.deleteOnExit();
		String reportId;
		if (reportSelection.getValue() != null) {
			reportId = reportSelection.getValue().getId();
		} else {
			throw new TimeTrackerException("Could not retrieve report id!");
		}
		return generateStreamReport(reportId, outputFile);
	}
	
	private InputStream generateStreamReport(String reportId, File outputFile) {
		try {
			reportGenerator.generateReport(assignments, reportId, outputFile.getCanonicalFile().toString());			
		} catch (Exception ex) {
			throw new TimeTrackerException(getTranslation("time.tracking.report.exception", outputFile.toString()), ex);
		}
		
		InputStream is;
		try {
			is = new FileInputStream(outputFile);
		} catch (FileNotFoundException e) {
			throw new TimeTrackerException(getTranslation("time.tracking.report.exception", outputFile.toString()), e);
		}
		return is;
	}
}