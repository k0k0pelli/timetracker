package id.meier.timetracking.application.services.consistency.rules;

import id.meier.timetracking.application.port.in.assignmentmangement.consistency.ConsistencyProblem;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IConsistencyMessage;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IConsistencyRule;
import id.meier.timetracking.application.services.consistency.ConsistencyMessage;

import id.meier.timetracking.domain.Assignment;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class AbstractConsistencyRule implements IConsistencyRule {
    @Override
    public List<IConsistencyMessage> checkConsistency(Assignment assignment) {
        List<IConsistencyMessage> messages = new ArrayList<>();
        messages.addAll(check(assignment));
        return messages;
    }

    protected abstract List<IConsistencyMessage> check(Assignment assignment);

    protected IConsistencyMessage checkConsistencyAndCreateMessage(Assignment assignment, ConsistencyProblem problem, Predicate<Assignment> check)  {
        IConsistencyMessage message = null;
        if (check.test(assignment)) {
            message = new ConsistencyMessage(assignment).addMessage(problem);
        }
        return message;
    }

    protected void add( List<IConsistencyMessage> messages, IConsistencyMessage message) {
        Optional.ofNullable(message).ifPresent(m -> messages.add(m));
    }


}
