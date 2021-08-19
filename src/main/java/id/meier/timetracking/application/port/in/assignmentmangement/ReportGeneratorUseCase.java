package id.meier.timetracking.application.port.in.assignmentmangement;


import id.meier.timetracking.domain.Assignment;

import java.util.List;
import java.util.Map;

public interface ReportGeneratorUseCase {
    void generateReport(List<Assignment> assignments, String reportId, String outputFilename);

    void generateReport(List<Assignment> assignments, String reportId, Map<String, Object> parameters,
                               String outputFilename);
}
