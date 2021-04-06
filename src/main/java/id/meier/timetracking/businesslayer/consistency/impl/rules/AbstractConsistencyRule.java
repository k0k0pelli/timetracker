package id.meier.timetracking.businesslayer.consistency.impl.rules;

import id.meier.timetracking.businesslayer.consistency.ConsistencyProblem;
import id.meier.timetracking.businesslayer.consistency.IConsistencyMessage;
import id.meier.timetracking.businesslayer.consistency.IConsistencyRule;
import id.meier.timetracking.businesslayer.consistency.impl.ConsistencyMessage;
import id.meier.timetracking.db.entity.AssignmentEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class AbstractConsistencyRule implements IConsistencyRule {
    @Override
    public List<IConsistencyMessage> checkConsistency(AssignmentEntity assignment) {
        List<IConsistencyMessage> messages = new ArrayList<>();
        messages.addAll(check(assignment));
        return messages;
    }

    protected abstract List<IConsistencyMessage> check(AssignmentEntity assignment);

    protected IConsistencyMessage checkConsistencyAndCreateMessage(AssignmentEntity assignment, ConsistencyProblem problem, Predicate<AssignmentEntity> check)  {
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
