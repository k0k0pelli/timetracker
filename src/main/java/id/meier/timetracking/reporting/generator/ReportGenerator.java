package id.meier.timetracking.reporting.generator;

import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.reporting.ReportDefinitionFactory;
import id.meier.timetracking.util.LocaleRetriever;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
public class ReportGenerator {
	
	private final AssignmentProjectPhaseTaskComparator projectPhaseTaskComparator;
	private final AssignmentStartDateTimeComparator startDateTimeComparator;
	private final AssignmentEndDateTimeComparator endDateTimeComparator;
	private final DailyWorkingPeriodAggregator workingPeriodAggregator;
	private final Environment environment;
	private final LocaleRetriever localeRetriever;

	public ReportGenerator(AssignmentProjectPhaseTaskComparator projectPhaseTaskComparator,
						   AssignmentStartDateTimeComparator startDateTimeComparator,
						   AssignmentEndDateTimeComparator endDateTimeComparator,
							Environment environment,
							LocaleRetriever localeRetriever,
						   DailyWorkingPeriodAggregator workingPeriodAggregator) {
		this.projectPhaseTaskComparator = projectPhaseTaskComparator;
		this.startDateTimeComparator = startDateTimeComparator;
		this.endDateTimeComparator = endDateTimeComparator;
		this.localeRetriever = localeRetriever;
		this.environment = environment;
		this.workingPeriodAggregator = workingPeriodAggregator;
	}

	public void generateReport(List<Assignment> assignments, String reportId, String outputFilename) {
	    assignments.sort(getComparator(reportId));
		generateReport(assignments, reportId, new HashMap<>(), outputFilename);
	}

	public void generateReport(List<Assignment> assignments, String reportId, Map<String, Object> parameters,
			String outputFilename) {
		// get the report input filename from the properties
		String inputFilename = environment.getProperty(reportId);
		Assignment first = this.getFirstAssignment(assignments);
		Assignment last = this.getLastAssignment(assignments);

		LocalDate startdate = (first != null)?first.getStartDate() : null;
		LocalTime starttime = (first != null)?first.getStartTime() : null;
		LocalDate enddate = (first != null)?last.getStartDate() : null;
		LocalTime endtime = (first != null)?last.getStartTime() : null;
		parameters.put("START_DATE", startdate);
		parameters.put("START_TIME", starttime);
		parameters.put("END_DATE", enddate);
		parameters.put("END_TIME", endtime);
		parameters.put(JRParameter.REPORT_LOCALE, localeRetriever.getLocale());
		JRBeanCollectionDataSource beanColDataSource = getBeanColDataSource(assignments, reportId);

		if (inputFilename != null) {
			try (InputStream is = this.getClass().getResourceAsStream(inputFilename)) {
				JasperPrint printer = JasperFillManager.fillReport(is, parameters, beanColDataSource);
				if (printer != null) {
					JasperExportManager.exportReportToPdfFile(printer, outputFilename);
				}
			} catch (IOException | JRException e) {
				throw new IllegalArgumentException("Creation of report failed!", e);
			}
		}
	}

	private JRBeanCollectionDataSource getBeanColDataSource(List<Assignment> assignments, String reportId) {
		JRBeanCollectionDataSource beanColDataSource;
		Collection<?> collection = assignments;
		if (reportId.equals(ReportDefinitionFactory.TIME_TRACKER_REPORT_WORKING_PERIOD_FILENAME)) {
			Duration duration = Duration.ofMinutes(1);
			collection = workingPeriodAggregator.aggregateAssignments(assignments, duration);
		}
		beanColDataSource = new JRBeanCollectionDataSource(collection);

		return beanColDataSource;
	}

	private Comparator<Assignment> getComparator(String reportId) {
		Comparator<Assignment> result = projectPhaseTaskComparator;
		if (reportId.equals(ReportDefinitionFactory.TIME_TRACKER_REPORT_WORKDAYS_FILENAME)) {
			result = startDateTimeComparator;
		}
		return result;
	}

	private Assignment getFirstAssignment(List<Assignment> assignments) {
		Optional<Assignment> min = assignments.stream().min(this.startDateTimeComparator);
		return min.orElse(null);
	}

	private Assignment getLastAssignment(List<Assignment> assignments) {
		Optional<Assignment> max = assignments.stream().max(this.endDateTimeComparator);
		return  max.orElse(null);
	}

}
