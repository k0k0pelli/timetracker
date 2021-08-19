package id.meier.timetracking.adapter.in.web;

import id.meier.timetracking.application.port.in.assignmentmangement.ConsistencyCheckUseCase;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IConsistencyMessage;
import id.meier.timetracking.domain.Assignment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsistencyCheckController {
    private ConsistencyCheckUseCase consistencyChecker;

    public ConsistencyCheckController(ConsistencyCheckUseCase consistencychecker) {
        this.consistencyChecker = consistencychecker;
    }

    public List<IConsistencyMessage> checkConsistency(Assignment assignment) {
        return consistencyChecker.checkConsistency(assignment);
    }
}
