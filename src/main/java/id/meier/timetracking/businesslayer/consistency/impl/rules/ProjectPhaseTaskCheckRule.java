package id.meier.timetracking.businesslayer.consistency.impl.rules;

import id.meier.timetracking.businesslayer.consistency.ConsistencyProblem;
import id.meier.timetracking.businesslayer.consistency.IConsistencyMessage;
import id.meier.timetracking.model.Assignment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ProjectPhaseTaskCheckRule extends AbstractConsistencyRule {
    @Override
    protected List<IConsistencyMessage> check(Assignment assignment) {
        List<IConsistencyMessage> messages = new ArrayList<>();
        add(messages, checkConsistencyAndCreateMessage(assignment, ConsistencyProblem.MISSING_PROJECT, a -> a.getProject() == null));
        add(messages, checkConsistencyAndCreateMessage(assignment, ConsistencyProblem.MISSING_PHASE, a -> a.getPhase() == null));
        add(messages, checkConsistencyAndCreateMessage(assignment, ConsistencyProblem.MISSING_TASK, a -> a.getTask() == null));
        return messages;
    }



}
