package id.meier.timetracking.moduletest.consistency.rules;

import id.meier.timetracking.TestBase;
import id.meier.timetracking.businesslayer.consistency.ConsistencyProblem;
import id.meier.timetracking.businesslayer.consistency.IConsistencyMessage;
import id.meier.timetracking.businesslayer.consistency.impl.rules.StartDateTimeCheckRule;
import id.meier.timetracking.model.Assignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StartDateTimeCheckRuleTest extends TestBase {

    private StartDateTimeCheckRule testee;

    @BeforeEach
    public void setup() {
        testee = new StartDateTimeCheckRule();
    }

    @Test
    public void testNoStartDateTime() {
        Assignment a = createAssignment(LocalDate.of(2019,12,31), LocalTime.of(13,0,0), null, null, null, null, null, null, 1L);
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(0);
    }

    @Test
    public void testMissingStartDate() {
        Assignment a = createAssignment(null, LocalTime.of(13,0,0), null, null, null, null,  null, null, 1L);

        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(1);
        assertThat(messages).element(0)
                .satisfies(cm -> assertThat(cm.getAssignment()).isEqualTo(a))
                .satisfies(cm -> assertThat(cm.getProblems()).hasSize(1).element(0).isEqualTo(ConsistencyProblem.MISSING_START_DATE));
    }

    @Test
    public void testMissingStartTime() {
        Assignment a = createAssignment(LocalDate.of(2019,12,31), null, null, null, null, null,  null, null, 1L);
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(1);
        assertThat(messages).element(0)
                .satisfies(cm -> assertThat(cm.getAssignment()).isEqualTo(a))
                .satisfies(cm -> assertThat(cm.getProblems()).hasSize(1).element(0).isEqualTo(ConsistencyProblem.MISSING_START_TIME));
    }

}
