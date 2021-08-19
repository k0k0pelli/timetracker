package id.meier.timetracking.application.services;

import id.meier.timetracking.application.port.in.assignmentmangement.commands.RemoveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SaveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentCommand;
import id.meier.timetracking.domain.Assignment;
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
class ManagementAssignmentServiceTest {

    @Autowired
    private ManagementAssignmentService testee;



    @Test
    public void testSaveAndRemoveAssignment() {
        Assignment a = getAssignment1();
        a = testee.saveAssignment(SaveAssignmentCommand.of(a));
        List<Assignment> assignments = testee.selectAssignments(SelectAssignmentCommand.builder().build());
        Assertions.assertThat(assignments).hasSize(1);

        testee.removeAssignment(RemoveAssignmentCommand.of(a));
        assignments = testee.selectAssignments(SelectAssignmentCommand.builder().build());
        Assertions.assertThat(assignments).hasSize(0);

    }

    private Assignment getAssignment1() {
        Project p = getProject1();
        Assignment a = Assignment.builder()
                .withDescription("Test Assignment 1")
                .withProject(p)
                .withPhase(p.getPhases().get(0))
                .withTask(p.getPhases().get(0).getTasks().get(0))
                .build();
        return a;
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