package id.meier.timetracking.application.services;

import id.meier.timetracking.application.port.in.assignmentmangement.commands.SaveAssignmentCommand;
import id.meier.timetracking.application.port.in.importer.DataImporterUseCase;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SavePhaseCommand;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SaveProjectCommand;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SaveTaskCommand;
import id.meier.timetracking.domain.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class DataImporterService implements DataImporterUseCase {

    private final Map<String, PersistableElement> importedElements;

    private ManagementAssignmentService assignmentService;
    private ManageProjectStructureService projectStructureService;

    public DataImporterService(ManagementAssignmentService assignmentService, ManageProjectStructureService projectStructureService) {
        importedElements = new TreeMap<>();

        this.assignmentService = assignmentService;
        this.projectStructureService = projectStructureService;
    }

    public void prepareImport(String projectsFilename, String phaseFilename, String taskFilename, String assignmentsFilename) {
        if (taskFilename != null) {
            prepare(taskFilename, Task.class, (property, value) -> value);
        }
        if (phaseFilename != null) {
            prepare(phaseFilename, Phase.class, (property, value) -> {
                if (property.equals("tasks")) {
                    return createListOfReferredObjects(value, Task.class);
                } else {
                    return value;
                }
            });
        }
        if (projectsFilename != null) {
            prepare(projectsFilename, Project.class,  (property, value) -> {
                if (property.equals("phases")) {
                    return createListOfReferredObjects(value, Phase.class);
                } else {
                    return value;
                }
            });
        }
        if (assignmentsFilename != null) {
            prepare(assignmentsFilename, Assignment.class,  (property, value) -> {
                switch (property) {
                    case "phase":
                    case "task":
                    case "project":
                        return this.importedElements.get(value);
                    default:
                        return value;
                }
            });
        }
        save();
    }

    private void save() {
        for (Map.Entry<String, PersistableElement> entry : this.importedElements.entrySet()) {
            if (entry.getValue().getClass().equals(Task.class)) {
                projectStructureService.save(SaveTaskCommand.of((Task)entry.getValue()));
            }
        }
        for (Map.Entry<String, PersistableElement> entry : this.importedElements.entrySet()) {
            if (entry.getValue().getClass().equals(Phase.class)) {
                projectStructureService.save(SavePhaseCommand.of((Phase)entry.getValue()));
            }
        }
        for (Map.Entry<String, PersistableElement> entry : this.importedElements.entrySet()) {
            if (entry.getValue().getClass().equals(Project.class)) {
                projectStructureService.save(SaveProjectCommand.of((Project)entry.getValue()));
            }
        }
        for (Map.Entry<String, PersistableElement> entry : this.importedElements.entrySet()) {
            if (entry.getValue().getClass().equals(Assignment.class)) {
                assignmentService.saveAssignment(SaveAssignmentCommand.of((Assignment) entry.getValue()));
            }
        }
    }

    private <T extends DescribedElement> List<T> createListOfReferredObjects(String value, Class<T> clazz) {
        List<T> elements = new ArrayList<>();
        String[] ids = value.split(";");
        for (String id : ids) {
            T element = Optional.ofNullable(clazz.cast(importedElements.get(id))).orElse(null);
            if (element != null) elements.add(element);
        }
        return elements;
    }

    private <T extends PersistableElement> void prepare(String filename, Class<T> clazz, ValueResolver resolver)  {
        try (FileInputStream fis = new FileInputStream(filename)) {
            importCsv(fis, clazz, resolver);
        } catch (IOException e) {
            throw new IllegalArgumentException("Problem importing project elements", e);
        }
    }

    /**
     *
     * @param inputStreamCsv Input stream of the CSV file.
     * @param targetClass The class of the objects generated from each record in the csv file.
     * @param <T> The type
     * @throws IOException Throws exception if the file cannot be read.
     */
    private <T extends PersistableElement> void importCsv(InputStream inputStreamCsv, Class<T> targetClass, ValueResolver resolver) throws IOException {

        try (CSVParser reader = new CSVParser(createReader(inputStreamCsv), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            String[] headers = getHeaders(reader);
            try {
                importAllLines(reader, headers, targetClass, resolver);
            } catch (InstantiationException | IllegalAccessException e) {
                IllegalArgumentException ex = new IllegalArgumentException("Could not import data!");
                ex.initCause(e);
                throw ex;
            }
        }
    }

    private <T extends PersistableElement> void importAllLines(CSVParser reader, String[] headers, Class<T> clazz,
                                                               ValueResolver resolver)
            throws IllegalAccessException, InstantiationException {
        CSVRecord record;
        for (CSVRecord strings : reader) {
            record = strings;
            T element = clazz.newInstance();
            String id = processLine(element, headers, record, resolver);
            importedElements.put(id, element);
        }
    }

    private <T extends PersistableElement> String processLine(T element, String[] headers, CSVRecord record,
                                                              ValueResolver resolver ) {
        BeanWrapper bw = new BeanWrapperImpl(element);
        String id = "";
        for (int i = 0; i < record.size(); i++) {
            String propertyName = headers[i];
            if (propertyName.equals("id")) {
                id = record.get(i);
            } else {
                Object value = resolver.processValue(propertyName, record.get(i));
                bw.setPropertyValue(propertyName, value);
            }
        }
        return id;
    }

    private Reader createReader(InputStream is) {
        return new InputStreamReader(is);
    }

    private String[] getHeaders(CSVParser parser) {
        Map<String, Integer> headerMap = parser.getHeaderMap();
        String[] headers = new String[headerMap.size()];
        headerMap.keySet().toArray(headers);
        return headers;
    }
}

@FunctionalInterface
interface ValueResolver {
    Object processValue(String propertyName, String value);
}
