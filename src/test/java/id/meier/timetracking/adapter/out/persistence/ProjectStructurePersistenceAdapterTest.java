package id.meier.timetracking.adapter.out.persistence;


import id.meier.timetracking.application.port.out.commands.SaveProjectEntityCommand;
import id.meier.timetracking.application.port.out.commands.SaveTaskEntityCommand;
import id.meier.timetracking.application.port.out.commands.SavePhaseEntityCommand;


import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;
import org.assertj.core.api.Assertions;
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
class ProjectStructurePersistenceAdapterTest {

    @Autowired
    private ProjectStructurePersistenceAdapter projectStructurePersistenceAdapter;

    @Test
    public void testProjectStoring() {
        Phase phase;
        Task task;
        Project p = Project.builder()
                .withName("Test Project")
                .withPhase(
                        phase = Phase.builder()
                                .withName("Test Phase")
                                .withTask(
                                        task = Task.builder()
                                                .withName("Test Task").build()
                                ).build()
                ).build();

        SavePhaseEntityCommand phaseCommand = SavePhaseEntityCommand.of(phase);
        SaveTaskEntityCommand taskCommand = SaveTaskEntityCommand.of(task);

        SaveProjectEntityCommand projectCommand = SaveProjectEntityCommand.of(p);
        projectStructurePersistenceAdapter.saveTask(taskCommand);
        projectStructurePersistenceAdapter.savePhase(phaseCommand);
        projectStructurePersistenceAdapter.saveProject(projectCommand);
        List<Project> projects = projectStructurePersistenceAdapter.selectAllProjects();
        Assertions.assertThat(projects).hasSize(1);
        Assertions.assertThat(projects).element(0).hasFieldOrPropertyWithValue("id", 3L);
    }
}