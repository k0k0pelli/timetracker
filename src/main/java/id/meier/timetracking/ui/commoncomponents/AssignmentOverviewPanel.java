package id.meier.timetracking.ui.commoncomponents;

import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import id.meier.timetracking.TimeTrackerException;
import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.util.AssignmentExporter;
import org.springframework.core.annotation.Order;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static id.meier.timetracking.util.DateTimeFormatter.d2S;
import static id.meier.timetracking.util.DateTimeFormatter.t2S;

@SpringComponent
@UIScope
@Order(10)
public class AssignmentOverviewPanel extends VerticalLayout {
	private Grid<Assignment> grid;
	private Label summary;
	private final AssignmentExporter exporter;
    private List<Assignment> assignments;
	public AssignmentOverviewPanel(AssignmentExporter exporter) {
		this.exporter = exporter;
		this.assignments = new ArrayList<>();
		grid = createGridPanel();
		HorizontalLayout summaryAndExport = createSummaryAndExportFunctionsPanel();
		setMargin(false);
		setPadding(false);
		
		add(grid, summaryAndExport);
	}

	public void setItems(List<Assignment> assignments) {
		grid.setItems(assignments);
		setSummaryLabel(assignments);
		this.assignments = assignments;
	}
	
	private Grid<Assignment> createGridPanel() {
		this.grid = new Grid<>();

		grid.setHeight("200px");

		addColumnToGrid(a -> d2S(a.getStartDate()),
				getTranslation("time.tracking.overview.table.start.date")).setSortable(true);
		addColumnToGrid(a -> t2S(a.getStartTime()),
				getTranslation("time.tracking.overview.table.start.time")).setSortable(true);
		addColumnToGrid(a -> d2S(a.getEndDate()),
				getTranslation("time.tracking.overview.table.end.date")).setSortable(true);
		addColumnToGrid(a -> t2S(a.getEndTime()),
				getTranslation("time.tracking.overview.table.end.time")).setSortable(true);
		addColumnToGrid(a -> getFormattedDouble(a.getIndustrialHoursDiff()),
				getTranslation("time.tracking.overview.table.time.diff")).setSortable(true);
		/*
		 * Grr. Doesn't work to align header column to the right. HorizontalLayout l =
		 * new HorizontalLayout(new Label("Time Diff (H)"));
		 * l.setAlignItems(Alignment.END); grid.addColumn( TemplateRenderer.<Assignment>
		 * of("<div style='text-align:right'>[[item.industrialHoursDiff]]</div>")
		 * .withProperty("industrialHoursDiff", a ->
		 * getFormattedDouble(a.getIndustrialHoursDiff())))
		 * .setHeader(l).setSortable(true);
		 */
		addColumnToGrid(Assignment::getProjectName, getTranslation("time.tracking.overview.table.project.name")).setSortable(true);

		addColumnToGrid(Assignment::getPhaseName, getTranslation("time.tracking.overview.table.phase.name")).setSortable(true);

		addColumnToGrid(Assignment::getTaskName, getTranslation("time.tracking.overview.table.task.name")).setSortable(true);

		addColumnToGrid(Assignment::getDescription, getTranslation("time.tracking.overview.table.description")).setSortable(true);
		grid.asSingleSelect();
		return grid;
	}

	public void setValueChangeListener(ValueChangeListener<ValueChangeEvent<Assignment>> valueChangeListener) {
		grid.asSingleSelect().addValueChangeListener(valueChangeListener);
	}

	public void scrollToItem(int index) {
		grid.scrollToIndex(index);
	}

	private HorizontalLayout createSummaryAndExportFunctionsPanel() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setAlignItems(Alignment.BASELINE);
		summary = new Label();
   	 	summary.getStyle().set("font-weight", "bold");
   	 	layout.add(summary, createExportButton());
		return layout;
	}

	private Button createExportButton() {
		return new Button(getTranslation("time.tracking.overview.table.export.results.btn"),
				VaadinIcon.ARROW_CIRCLE_DOWN.create(), event -> {
			boolean isCheckPassed = true;

			final StreamResource resource = new StreamResource("exportedAssignments.csv",
					this::downloadExportFile);
			final StreamRegistration registration = VaadinSession.getCurrent().getResourceRegistry().registerResource(resource);
			UI.getCurrent().getPage().setLocation(registration.getResourceUri());

		});
	}

	private void setSummaryLabel(List<Assignment> assignments) {
		Double sum = assignments.stream().mapToDouble(Assignment::getIndustrialHoursDiff).reduce(0, Double::sum);
		DecimalFormat df = new DecimalFormat("0.00"); 
		String msg = getTranslation("time.tracking.overview.table.summary.label", assignments.size(), sum);
		this.summary.setText(msg);
	}
	
	private Column<Assignment> addColumnToGrid(ValueProvider<Assignment, ?> valueProvider, String headerText) {
		return grid.addColumn(valueProvider).setHeader(headerText);
	}

	private String getFormattedDouble(Double d) {
		String result = null;
		if (d != null) {
			DecimalFormat df = new DecimalFormat("0.00");
			result = df.format(d);
		}
		return result;
	}
	
	private InputStream downloadExportFile() {
		File exportedAssignments;
		try {
			exportedAssignments = this.exporter.createCSVFile(this.assignments);
		} catch (Exception ex) {
			throw new TimeTrackerException(getTranslation("time.tracking.overview.table.export.exception", "file undefined"), ex);
		}
		
		InputStream is;
		try {
			is = new FileInputStream(exportedAssignments);
		} catch (FileNotFoundException e) {
			throw new TimeTrackerException(getTranslation("time.tracking.overview.table.export.exception", exportedAssignments.toString()), e);
		}
		return is;
	}
}
