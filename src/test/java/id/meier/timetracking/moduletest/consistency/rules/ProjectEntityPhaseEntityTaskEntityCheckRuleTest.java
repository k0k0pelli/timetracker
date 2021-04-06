package id.meier.timetracking.moduletest.consistency.rules;

import id.meier.timetracking.TestBase;
import id.meier.timetracking.businesslayer.consistency.ConsistencyProblem;
import id.meier.timetracking.businesslayer.consistency.IConsistencyMessage;
import id.meier.timetracking.businesslayer.consistency.impl.rules.ProjectPhaseTaskCheckRule;
import id.meier.timetracking.db.entity.AssignmentEntity;
import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.ProjectEntity;
import id.meier.timetracking.db.entity.TaskEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectEntityPhaseEntityTaskEntityCheckRuleTest extends TestBase {

    private ProjectPhaseTaskCheckRule testee;

    @BeforeEach
    public void setup() {
        testee = new ProjectPhaseTaskCheckRule();
    }

    @Test
    public void testNoMissingProjectPhaseTask() {
        AssignmentEntity a = createAssignment(null, null, null, null, null, new ProjectEntity(), new PhaseEntity(), new TaskEntity(), 1L);
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(0);
    }

    @Test
    public void testMissingProject() {
        AssignmentEntity a = createAssignment(null, null, null, null, null, null, new PhaseEntity(), new TaskEntity(), 1L);
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(1);
        assertThat(messages).element(0)
                .satisfies(cm -> assertThat(cm.getAssignment()).isEqualTo(a))
                .satisfies(cm -> assertThat(cm.getProblems()).hasSize(1).element(0).isEqualTo(ConsistencyProblem.MISSING_PROJECT));
    }

    @Test
    public void testMissingPhase() {
        AssignmentEntity a = createAssignment(null, null, null, null, null, new ProjectEntity(), null, new TaskEntity(), 1L);
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(1);
        assertThat(messages).element(0)
                .satisfies(cm -> assertThat(cm.getAssignment()).isEqualTo(a))
                .satisfies(cm -> assertThat(cm.getProblems()).hasSize(1).element(0).isEqualTo(ConsistencyProblem.MISSING_PHASE));
    }

    @Test
    public void testMissingTask() {
        AssignmentEntity a = createAssignment(null, null, null, null, null, new ProjectEntity(), new PhaseEntity(), null, 1L);
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(1);
        assertThat(messages).element(0)
                .satisfies(cm -> assertThat(cm.getAssignment()).isEqualTo(a))
                .satisfies(cm -> assertThat(cm.getProblems()).hasSize(1).element(0).isEqualTo(ConsistencyProblem.MISSING_TASK));
    }


    @Test
    public void testMissingProjectPhaseTask() {
        AssignmentEntity a = createAssignment(null, null, null, null, null, null, null, null, 1L);
        List<IConsistencyMessage> messages = testee.checkConsistency(a);
        assertThat(messages).hasSize(3);
    }

}
