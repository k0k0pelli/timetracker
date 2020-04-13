package id.meier.timetracking.businesslayer.consistency.impl.rules;

import id.meier.timetracking.businesslayer.consistency.ConsistencyProblem;
import id.meier.timetracking.businesslayer.consistency.IConsistencyMessage;
import id.meier.timetracking.model.Assignment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StartDateTimeCheckRule extends AbstractConsistencyRule {
    @Override
    protected List<IConsistencyMessage> check(Assignment assignment) {
        List<IConsistencyMessage> messages = new ArrayList<>();
        add(messages, checkStartDateNull(assignment));
        add(messages, checkStartTimeNull(assignment));
        return messages;
    }

    private IConsistencyMessage checkStartDateNull(Assignment assignment)  {
        return checkConsistencyAndCreateMessage(assignment, ConsistencyProblem.MISSING_START_DATE, a -> a.getStartDate() == null);
    }

    private IConsistencyMessage checkStartTimeNull(Assignment assignment)  {
        return checkConsistencyAndCreateMessage(assignment, ConsistencyProblem.MISSING_START_TIME, a -> a.getStartTime() == null);
    }

}
