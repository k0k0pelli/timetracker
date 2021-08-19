package id.meier.timetracking;


import id.meier.timetracking.adapter.in.web.DataImpoterController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;


@SpringBootApplication
public class Application {
    @Autowired
    private Environment env;

    @Autowired
    private DataImpoterController dataImporter;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initProjectsMetadata() {
        String projectsFile = env.getProperty("projectsFile");
        String phaseFile = env.getProperty("phasesFile");
        String taskFile = env.getProperty("tasksFile");
        String assignmentsFile = env.getProperty("assignmentsFile");
        dataImporter.prepareImport(projectsFile, phaseFile, taskFile, assignmentsFile);

    }



}
