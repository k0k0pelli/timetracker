package id.meier.timetracking.moduletest.consistency.rules;

import id.meier.timetracking.TestBase;
import id.meier.timetracking.application.port.in.assignmentmangement.ManageAssignmentUseCase;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SaveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentAfterGivenStartDateAndTimeCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.ConsistencyProblem;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IConsistencyMessage;

import id.meier.timetracking.application.services.ManagementAssignmentService;
import id.meier.timetracking.application.services.consistency.rules.EndDateTimeCheckRule;
import id.meier.timetracking.domain.Assignment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EndDateTimeCheckRuleTest extends TestBase {

    @Autowired
    private ManagementAssignmentService managementAssignmentService;

    private static final SelectAssignmentAfterGivenStartDateAndTimeCommand firstStartDateTimeCommand =
            SelectAssignmentAfterGivenStartDateAndTimeCommand.of(LocalDate.of(2019,12,31),
                                                                 LocalTime.of(13,0,0));

    private static final SelectAssignmentAfterGivenStartDateAndTimeCommand secondStartDateTimeCommand =
            SelectAssignmentAfterGivenStartDateAndTimeCommand.of(LocalDate.of(2019,12,31),
                    LocalTime.of(13,0,0));

    @Test
    public void testNoMissingTimeAndNoOverlapping() {
        prepareNoOverlappingAssignments();
        EndDateTimeCheckRule testee = createTestee(managementAssignmentService);
        Assignment a = createAssignment();
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(0);
    }

    @Test
    public void testMissingOverlappingDates() {
        prepareOverlappingAssignments();
        EndDateTimeCheckRule testee = createTestee(managementAssignmentService);
        Assignment a = createAssignment();
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(1);
        assertThat(messages).element(0)
                .satisfies(cm -> assertThat(cm.getAssignment()).isEqualTo(a))
                .satisfies(cm -> assertThat(cm.getProblems()).hasSize(1).element(0).isEqualTo(ConsistencyProblem.DATE_TIME_OVERLAP));
    }

    private Assignment createAssignment() {
        return createAssignment(null, null, LocalDate.of(2019, 12, 31), LocalTime.of(13,0,0), null, null,  null, null, 1L);
    }

    private EndDateTimeCheckRule createTestee(ManageAssignmentUseCase repositoryAccessor) {
        return new EndDateTimeCheckRule(repositoryAccessor);
    }

    private void prepareNoOverlappingAssignments() {

    }

    private void prepareOverlappingAssignments() {
        Assignment a = createAssignment(LocalDate.of(2019,12,31), LocalTime.of(12,30,0), LocalDate.of(2019,12,31), LocalTime.of(15,30,0), null, null, null, null, 1L);
        managementAssignmentService.saveAssignment(SaveAssignmentCommand.of(a));
    }

    /*
    private ManageAssignmentUseCase createRepoAccessorMockForNoOverlappingAssignments() {
        List<Assignment> list = new ArrayList<>();
        ManageAssignmentUseCase managementUseCase = Mockito.mock(ManageAssignmentUseCase.class);
        when(managementUseCase.selectAssignmentsStartDateTimeAfterGivenDateTime(eq(firstStartDateTimeCommand)))
                .thenReturn(list);
        return managementUseCase;
    }


    private ManageAssignmentUseCase createRepoAccessorMockForOverlappingAssignments() {
        List<Assignment> list = new ArrayList<>();
        list.add(createAssignment(LocalDate.of(2019,12,31), LocalTime.of(13,0,0), null, null, null, null, null, null, 1L));
        ManageAssignmentUseCase repositoryAccessor = Mockito.mock(ManageAssignmentUseCase.class);
        when(repositoryAccessor.selectAssignmentsStartDateTimeAfterGivenDateTime(eq(firstStartDateTimeCommand)))
                .thenReturn(list);
        return repositoryAccessor;
    }
    */
}
