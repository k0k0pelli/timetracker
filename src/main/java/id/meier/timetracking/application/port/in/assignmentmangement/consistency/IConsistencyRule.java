package id.meier.timetracking.application.port.in.assignmentmangement.consistency;



import id.meier.timetracking.domain.Assignment;

import java.util.List;

public interface IConsistencyRule {
    List<IConsistencyMessage> checkConsistency(Assignment assignment);
}
