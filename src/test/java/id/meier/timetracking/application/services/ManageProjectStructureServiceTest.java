package id.meier.timetracking.application.services;

import id.meier.timetracking.application.port.in.structuremanagment.commands.RemoveProjectCommand;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SaveProjectCommand;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SelectProjectCommand;
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
class ManageProjectStructureServiceTest {

    @Autowired
    private ManageProjectStructureService testee;


    @BeforeEach
    void setUp() {
    }

    @Test
    public void testStoreProject() {
        testee.saveProject(SaveProjectCommand.of(getProject1()));
        List<Project> projects = testee.getProjects();
        Assertions.assertThat(projects).hasSize(1);
    }

    @Test
    public void testRemoveProject() {
        //-- add project
        Project p = getProject1();
        SaveProjectCommand projectSave = SaveProjectCommand.of(p);
        testee.saveProject(projectSave);
        List<Project> projects = testee.getProjects();
        Assertions.assertThat(projects).hasSize(1);

        // -- remove project
        RemoveProjectCommand projectRemove = RemoveProjectCommand.of(p);
        testee.removeProject(projectRemove);
        List<Project> projects2 = testee.getProjects();
        Assertions.assertThat(projects2).hasSize(0);
    }

    private Project getProject1() {
        return Project.builder().withName("TestProject")
                .withPhase(getPhase1()).build();
    }

    private Phase getPhase1() {
        return Phase.builder().withName("Phase 1")
                .withTask(getTask1()).build();
    }

    private Task getTask1() {
        return Task.builder().withName("Task 1").build();
    }
}