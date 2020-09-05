package id.meier.timetracking.util;

import id.meier.timetracking.TimeTrackerException;
import id.meier.timetracking.model.Assignment;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static id.meier.timetracking.util.DateTimeFormatter.d2S;
import static id.meier.timetracking.util.DateTimeFormatter.t2S;

@Component
@Order(2)
public class AssignmentExporter {
	private final String[] HEADERS;
	private final TranslationProvider translationProvider;
	
	public AssignmentExporter(TranslationProvider translationProvider) {
		this.translationProvider = translationProvider;

		HEADERS = new String[] { translationProvider.getTranslation("time.tracking.exporter.header.start.date"),
				translationProvider.getTranslation("time.tracking.exporter.header.start.time"),
				translationProvider.getTranslation("time.tracking.exporter.header.end.date"),
				translationProvider.getTranslation("time.tracking.exporter.header.end.time"),
				translationProvider.getTranslation("time.tracking.exporter.header.time.diff"),
				translationProvider.getTranslation("time.tracking.exporter.header.project.name"),
				translationProvider.getTranslation("time.tracking.exporter.header.phase.name"),
				translationProvider.getTranslation("time.tracking.exporter.header.task.name"),
				translationProvider.getTranslation("time.tracking.exporter.header.description")};
	}
	
	public File createCSVFile(List<Assignment> assignments) throws IOException {
		File temp = File.createTempFile("export" + new Date().getTime(), ".csv"); 
	    FileWriter out = new FileWriter(temp.getAbsoluteFile());
	    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.EXCEL.withHeader(HEADERS))) {	        
	    	assignments.forEach(a -> {
	    		try {
					printer.printRecord(d2S(a.getStartDate()), t2S(a.getStartTime()),
							d2S(a.getEndDate()), t2S(a.getEndTime()), a.getIndustrialHoursDiff(),
							a.getProjectName(), a.getPhaseName(), 
							a.getTaskName(), a.getDescription());
				} catch (IOException e) {
					throw new TimeTrackerException(translationProvider.getTranslation("time.tracking.exporter.exception"), e);
				}
	    	});
	    }
	    return temp;
	}
}
