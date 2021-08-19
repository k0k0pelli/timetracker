package id.meier.timetracking.application.services;

import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;




@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DataImporterServiceTest {

    private DataImporterService testee;

    @Autowired
    private ManagementAssignmentService assignmentService;

    @Autowired
    private ManageProjectStructureService projectStructureService;


    @BeforeEach
    public void setup() {
        testee = new DataImporterService(assignmentService, projectStructureService);
    }

    @Test
    public void testProjectsImport() {
        // given
        testee.prepareImport("./src/test/resources/id.meier.timetracking.integrationtest.util/projects.csv",
                "./src/test/resources/id.meier.timetracking.integrationtest.util/phases.csv",
                "./src/test/resources/id.meier.timetracking.integrationtest.util/tasks.csv",
                "./src/test/resources/id.meier.timetracking.integrationtest.util/assignments.csv");

        // when
        //testee.commitImportedProjectMetaData();

        // then
        List<Project> projects = projectStructureService.getProjects();
        Assertions.assertThat(projects).hasSize(3);
        Assertions.assertThat(projects).element(0).hasFieldOrPropertyWithValue("name","p1");
        Assertions.assertThat(projects).element(1).hasFieldOrPropertyWithValue("name","p2");
        Assertions.assertThat(projects).element(2).hasFieldOrPropertyWithValue("name","p3");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testPhaseImport() {
        // given
        testee.prepareImport("./src/test/resources/id.meier.timetracking.integrationtest.util/projects.csv",
                "./src/test/resources/id.meier.timetracking.integrationtest.util/phases.csv",
                "./src/test/resources/id.meier.timetracking.integrationtest.util/tasks.csv",
                "./src/test/resources/id.meier.timetracking.integrationtest.util/assignments.csv");

        // when
        //testee.commitImportedProjectMetaData();

        // then
        List<Project> projects = projectStructureService.getProjects();
        Assertions.assertThat(projects)
                .element(0)
                .satisfies(p -> Assertions.assertThat(p.getPhases()).hasSize(2));
        Assertions.assertThat(projects).flatExtracting(Project::getPhases).flatExtracting(Phase::getName)
                .containsExactly("phase 1", "phase 2");
    }

    @Test
    public void testTaskImport() {
        // given
        testee.prepareImport("./src/test/resources/id.meier.timetracking.integrationtest.util/projects.csv",
                "./src/test/resources/id.meier.timetracking.integrationtest.util/phases.csv",
                "./src/test/resources/id.meier.timetracking.integrationtest.util/tasks.csv",
                "./src/test/resources/id.meier.timetracking.integrationtest.util/assignments.csv");

        // when
        //testee.commitImportedProjectMetaData();

        // then
        List<Project> projects = projectStructureService.getProjects();
        Assertions.assertThat(projects)
                .element(0)
                .satisfies(p -> Assertions.assertThat(p.getPhases().get(0).getTasks())
                        .flatExtracting(Task::getName)
                        .containsExactly("task 1", "task 2", "task 3")
                );

    }
}