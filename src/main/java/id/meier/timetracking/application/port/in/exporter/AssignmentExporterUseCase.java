package id.meier.timetracking.application.port.in.exporter;

import id.meier.timetracking.domain.Assignment;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface AssignmentExporterUseCase {
    File createCSVFile(List<Assignment> assignments) throws IOException;
}
