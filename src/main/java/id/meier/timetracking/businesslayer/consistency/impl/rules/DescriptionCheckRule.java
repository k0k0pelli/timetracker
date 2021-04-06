package id.meier.timetracking.businesslayer.consistency.impl.rules;

import id.meier.timetracking.businesslayer.consistency.ConsistencyProblem;
import id.meier.timetracking.businesslayer.consistency.IConsistencyMessage;
import id.meier.timetracking.db.entity.AssignmentEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DescriptionCheckRule extends AbstractConsistencyRule {
    @Override
    protected List<IConsistencyMessage> check(AssignmentEntity assignment) {
        List<IConsistencyMessage> messages = new ArrayList<>();
        add(messages, checkStartDateNull(assignment));
        return messages;
    }

    private IConsistencyMessage checkStartDateNull(AssignmentEntity assignment)  {
        return checkConsistencyAndCreateMessage(assignment, ConsistencyProblem.MISSING_DESCRIPTION, a -> a.getDescription() == null || a.getDescription().isEmpty());
    }


}
