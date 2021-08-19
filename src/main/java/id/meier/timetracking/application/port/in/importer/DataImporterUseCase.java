package id.meier.timetracking.application.port.in.importer;

public interface DataImporterUseCase {
    void prepareImport(String projectsFilename, String phaseFilename, String taskFilename, String assignmentsFilename);
}
