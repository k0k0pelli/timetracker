package id.meier.timetracking.reporting;

import id.meier.timetracking.util.TranslationProvider;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
@Order(8)
public class ReportDefinitionFactory {
	
	private static final String TIME_TRACKER_REPORT_PROJECT_PHASE_TASK_FILENAME = "id.meier.time.tracker.report.project.phase.task.filename";
	private static final String TIME_TRACKER_REPORT_PROJECT_PHASE_FILENAME = "id.meier.time.tracker.report.project.phase.filename";
	private static final String TIME_TRACKER_REPORT_PROJECT_FILENAME = "id.meier.time.tracker.report.project.filename";
	public static final String TIME_TRACKER_REPORT_WORKDAYS_FILENAME = "id.meier.time.tracker.report.workdays.filename";
	public static final String TIME_TRACKER_REPORT_WORKING_PERIOD_FILENAME = "id.meier.time.tracker.report.working.period.filename";

	private static final String TIME_TRACKING_REPORTING_WORKING_DAY_REPORT = "time.tracking.reporting.working.day.report";
	private static final String TIME_TRACKING_REPORTING_WORKING_PERIOD_REPORT = "time.tracking.reporting.working.period.report";
	private static final String TIME_TRACKING_REPORTING_PROJECT_REPORT = "time.tracking.reporting.project.report";
	private static final String TIME_TRACKING_REPORTING_PROJECT_PHASE_REPORT = "time.tracking.reporting.project.phase.report";
	private static final String TIME_TRACKING_REPORTING_PROJECT_PHASE_TASK_REPORT = "time.tracking.reporting.project.phase.task.report";
	private static final Map<String, String> TRANSLATION_MAP;

	static {
		TRANSLATION_MAP = new TreeMap<>();
		TRANSLATION_MAP.put(TIME_TRACKING_REPORTING_PROJECT_PHASE_TASK_REPORT, TIME_TRACKER_REPORT_PROJECT_PHASE_TASK_FILENAME);
		TRANSLATION_MAP.put(TIME_TRACKING_REPORTING_PROJECT_PHASE_REPORT, TIME_TRACKER_REPORT_PROJECT_PHASE_FILENAME);
	    TRANSLATION_MAP.put(TIME_TRACKING_REPORTING_PROJECT_REPORT, TIME_TRACKER_REPORT_PROJECT_FILENAME);
		TRANSLATION_MAP.put(TIME_TRACKING_REPORTING_WORKING_DAY_REPORT, TIME_TRACKER_REPORT_WORKDAYS_FILENAME);
		TRANSLATION_MAP.put(TIME_TRACKING_REPORTING_WORKING_PERIOD_REPORT, TIME_TRACKER_REPORT_WORKING_PERIOD_FILENAME);
	}
	
	private final TranslationProvider translationProvider;
	
	public ReportDefinitionFactory(TranslationProvider translationProvider) {
		this.translationProvider = translationProvider;
	}
	
	List<ReportDefinition> getReportDefinitions() {
		List<ReportDefinition> reportDefinition = new ArrayList<>();
		for (String key : TRANSLATION_MAP.keySet()) {
			String id = TRANSLATION_MAP.get(key);
			reportDefinition.add(new ReportDefinition(translationProvider.getTranslation(key), id));
		}
		return reportDefinition;
	}
}
