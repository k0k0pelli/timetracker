package id.meier.timetracking.util;

import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.businesslayer.context.DefaultRepositoryContext;
import id.meier.timetracking.dblayer.repository.RepositoryAccessor;
import id.meier.timetracking.model.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class ProjectStructureImporter {

    private final Map<String, PersistableElement> importedElements;
    private final RepositoryAccessor repositoryAccessor;

    public ProjectStructureImporter(RepositoryAccessor repositoryAccessor) {
        importedElements = new TreeMap<>();
        this.repositoryAccessor = repositoryAccessor;
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

    }

    public <T extends DescribedElement> void commitImportedProjectMetaData() {
        CommandsCollector collector = new CommandsCollector(new DefaultRepositoryContext());
        for (String key : importedElements.keySet()) {
            PersistableElement element = importedElements.get(key);
            collector.save(element);
        }
        collector.performPersistingOperations(repositoryAccessor);
    }

    private <T extends DescribedElement> List<T> createListOfReferredObjects(String value, Class<T> clazz) {
        List<T> elements = new ArrayList<>();
        String[] ids = value.split(";");
        for (String id : ids) {
            elements.add(clazz.cast(importedElements.get(id)));
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
            this.importedElements.put(id, element);
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