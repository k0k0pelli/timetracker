package id.meier.timetracking.businesslayer.consistency.impl.rules;

import id.meier.timetracking.businesslayer.consistency.ConsistencyProblem;
import id.meier.timetracking.businesslayer.consistency.IConsistencyMessage;
import id.meier.timetracking.businesslayer.consistency.impl.AdditionalMessageData;
import id.meier.timetracking.dblayer.repository.RepositoryAccessor;
import id.meier.timetracking.model.Assignment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EndDateTimeCheckRule extends AbstractConsistencyRule {
    private final RepositoryAccessor repoAccessor;
    public EndDateTimeCheckRule(RepositoryAccessor repoAccessor) {
        this.repoAccessor = repoAccessor;
    }

    @Override
    protected List<IConsistencyMessage> check(Assignment assignment) {
        List<IConsistencyMessage> messages = new ArrayList<>();
        add(messages, checkEndTimeNull(assignment));
        add(messages, checkEndDateNull(assignment));
        add(messages, checkTimeOverlap(assignment));
        return messages;
    }

    private IConsistencyMessage checkEndDateNull(Assignment assignment)  {
        return checkConsistencyAndCreateMessage(assignment, ConsistencyProblem.MISSING_END_DATE, a -> a.getEndDate() == null);
    }

    private IConsistencyMessage checkEndTimeNull(Assignment assignment)  {
        return checkConsistencyAndCreateMessage(assignment, ConsistencyProblem.MISSING_END_TIME, a -> a.getEndTime() == null);
    }

    private IConsistencyMessage checkTimeOverlap(Assignment assignment)  {
        List<Assignment> foundOverlaps = new ArrayList<>();
        IConsistencyMessage message = checkConsistencyAndCreateMessage(assignment, ConsistencyProblem.DATE_TIME_OVERLAP, a -> {
            boolean result = false;
            if ((assignment.getEndTime() != null) && (assignment.getEndDate() != null)) {
                List<Assignment> overlappingAssignments = repoAccessor.selectAssignmentsStartDateTimeAfterGivenDateTime(assignment.getEndDate(), assignment.getEndTime());
                if (overlappingAssignments.size() > 0) {
                    foundOverlaps.addAll(overlappingAssignments);
                    result = true;
                }
            }
            return result;
        }
        );
        addOverlappingAssignments(foundOverlaps, message);
        return message;
    }

    private void addOverlappingAssignments(List<Assignment> foundOverlaps, IConsistencyMessage message) {
        if (foundOverlaps.size() > 0) {
            List<AdditionalMessageData<?>> messageData = foundOverlaps
                    .stream()
                    .map(a -> new AdditionalMessageData<Assignment>(a, Assignment.class))
                    .collect(Collectors.toList());
            message.addMessageData(messageData);
        }
    }

}
