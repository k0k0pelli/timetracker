package id.meier.timetracking.moduletest.consistency.rules;

import id.meier.timetracking.TestBase;
import id.meier.timetracking.businesslayer.consistency.ConsistencyProblem;
import id.meier.timetracking.businesslayer.consistency.IConsistencyMessage;
import id.meier.timetracking.businesslayer.consistency.impl.rules.ProjectPhaseTaskCheckRule;
import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectPhaseTaskCheckRuleTest extends TestBase {

    private ProjectPhaseTaskCheckRule testee;

    @BeforeEach
    public void setup() {
        testee = new ProjectPhaseTaskCheckRule();
    }

    @Test
    public void testNoMissingProjectPhaseTask() {
        Assignment a = createAssignment(null, null, null, null, null, new Project(), new Phase(), new Task(), 1L);
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(0);
    }

    @Test
    public void testMissingProject() {
        Assignment a = createAssignment(null, null, null, null, null, null, new Phase(), new Task(), 1L);
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(1);
        assertThat(messages).element(0)
                .satisfies(cm -> assertThat(cm.getAssignment()).isEqualTo(a))
                .satisfies(cm -> assertThat(cm.getProblems()).hasSize(1).element(0).isEqualTo(ConsistencyProblem.MISSING_PROJECT));
    }

    @Test
    public void testMissingPhase() {
        Assignment a = createAssignment(null, null, null, null, null, new Project(), null, new Task(), 1L);
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(1);
        assertThat(messages).element(0)
                .satisfies(cm -> assertThat(cm.getAssignment()).isEqualTo(a))
                .satisfies(cm -> assertThat(cm.getProblems()).hasSize(1).element(0).isEqualTo(ConsistencyProblem.MISSING_PHASE));
    }

    @Test
    public void testMissingTask() {
        Assignment a = createAssignment(null, null, null, null, null, new Project(), new Phase(), null, 1L);
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(1);
        assertThat(messages).element(0)
                .satisfies(cm -> assertThat(cm.getAssignment()).isEqualTo(a))
                .satisfies(cm -> assertThat(cm.getProblems()).hasSize(1).element(0).isEqualTo(ConsistencyProblem.MISSING_TASK));
    }


    @Test
    public void testMissingProjectPhaseTask() {
        Assignment a = createAssignment(null, null, null, null, null, null, null, null, 1L);
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(3);
    }

}
