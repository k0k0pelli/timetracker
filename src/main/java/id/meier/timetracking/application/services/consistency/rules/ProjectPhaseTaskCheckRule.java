package id.meier.timetracking.application.services.consistency.rules;

import id.meier.timetracking.application.port.in.assignmentmangement.consistency.ConsistencyProblem;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IConsistencyMessage;

import id.meier.timetracking.domain.Assignment;

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
