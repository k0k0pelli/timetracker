package id.meier.timetracking.moduletest.consistency.rules;

import id.meier.timetracking.TestBase;
import id.meier.timetracking.businesslayer.consistency.ConsistencyProblem;
import id.meier.timetracking.businesslayer.consistency.IConsistencyMessage;
import id.meier.timetracking.businesslayer.consistency.impl.rules.EndDateTimeCheckRule;
import id.meier.timetracking.dblayer.repository.RepositoryAccessor;
import id.meier.timetracking.model.Assignment;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class EndDateTimeCheckRuleTest extends TestBase {


    @Test
    public void testNoMissingTimeAndNoOverlapping() {
        EndDateTimeCheckRule testee = createTestee(createRepoAccessorMockForNoOverlappingAssignments());
        Assignment a = createAssignment();
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(0);
    }

    @Test
    public void testMissingOverlappingDates() {
        EndDateTimeCheckRule testee = createTestee(createRepoAccessorMockForOverlappingAssignments());
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

    private EndDateTimeCheckRule createTestee(RepositoryAccessor repositoryAccessor) {
        return new EndDateTimeCheckRule(repositoryAccessor);
    }

    private RepositoryAccessor createRepoAccessorMockForNoOverlappingAssignments() {
        List<Assignment> list = new ArrayList<>();
        RepositoryAccessor repositoryAccessor = Mockito.mock(RepositoryAccessor.class);
        when(repositoryAccessor.selectAssignmentsStartDateTimeAfterGivenDateTime(eq(LocalDate.of(2019,12,31)), eq(LocalTime.of(13,0,0))))
                .thenReturn(list);
        return repositoryAccessor;
    }


    private RepositoryAccessor createRepoAccessorMockForOverlappingAssignments() {
        List<Assignment> list = new ArrayList<>();
        list.add(createAssignment(LocalDate.of(2019,12,31), LocalTime.of(13,0,0), null, null, null, null, null, null, 1L));
        RepositoryAccessor repositoryAccessor = Mockito.mock(RepositoryAccessor.class);
        when(repositoryAccessor.selectAssignmentsStartDateTimeAfterGivenDateTime(eq(LocalDate.of(2019,12,31)), eq(LocalTime.of(13,0,0))))
                .thenReturn(list);
        return repositoryAccessor;
    }

}
