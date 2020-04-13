package id.meier.timetracking.businesslayer.consistency;

import id.meier.timetracking.model.Assignment;

import java.util.List;

public interface IConsistencyChecker {
    List<IConsistencyMessage> checkConsistency(Assignment assignment);
}
