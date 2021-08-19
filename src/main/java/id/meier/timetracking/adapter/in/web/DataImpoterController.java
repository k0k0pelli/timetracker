package id.meier.timetracking.adapter.in.web;

import id.meier.timetracking.application.port.in.importer.DataImporterUseCase;
import org.springframework.stereotype.Component;

@Component
public class DataImpoterController {
    private DataImporterUseCase dataImporter;
    public DataImpoterController(DataImporterUseCase dataImporter) {
        this.dataImporter = dataImporter;
    }
    public void prepareImport(String projectsFilename, String phaseFilename, String taskFilename, String assignmentsFilename) {
        dataImporter.prepareImport(projectsFilename, phaseFilename, taskFilename, assignmentsFilename);
    }
}
