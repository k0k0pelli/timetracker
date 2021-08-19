package id.meier.timetracking.application.services.consistency.rules;

import id.meier.timetracking.application.port.in.assignmentmangement.consistency.ConsistencyProblem;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IConsistencyMessage;

import id.meier.timetracking.domain.Assignment;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DescriptionCheckRule extends AbstractConsistencyRule {
    @Override
    protected List<IConsistencyMessage> check(Assignment assignment) {
        List<IConsistencyMessage> messages = new ArrayList<>();
        add(messages, checkStartDateNull(assignment));
        return messages;
    }

    private IConsistencyMessage checkStartDateNull(Assignment assignment)  {
        return checkConsistencyAndCreateMessage(assignment, ConsistencyProblem.MISSING_DESCRIPTION, a -> a.getDescription() == null || a.getDescription().isEmpty());
    }

}
