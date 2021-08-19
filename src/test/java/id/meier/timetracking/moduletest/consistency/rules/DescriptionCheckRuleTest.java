package id.meier.timetracking.moduletest.consistency.rules;

import id.meier.timetracking.TestBase;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.ConsistencyProblem;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IConsistencyMessage;

import id.meier.timetracking.application.services.consistency.rules.DescriptionCheckRule;
import id.meier.timetracking.domain.Assignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DescriptionCheckRuleTest extends TestBase {

    private DescriptionCheckRule testee;

    @BeforeEach
    public void setup() {
        testee = new DescriptionCheckRule();
    }

    @Test
    public void testMissingDescription() {
        Assignment a = createAssignment(null, null, null, null, null, null, null, null, 1L);
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(1);
        assertThat(messages).element(0)
                .satisfies(cm -> assertThat(cm.getAssignment()).isEqualTo(a))
                .satisfies(cm -> assertThat(cm.getProblems()).hasSize(1).element(0).isEqualTo(ConsistencyProblem.MISSING_DESCRIPTION));
    }

    @Test
    public void testHavingDescription() {
        Assignment a = createAssignment(null, null, null, null, "test", null, null, null, 1L);
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(0);
    }

}
