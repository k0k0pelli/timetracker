package id.meier.timetracking.businesslayer.consistency;

import id.meier.timetracking.model.Assignment;

import java.util.List;

public interface IConsistencyRule {
    List<IConsistencyMessage> checkConsistency(Assignment assignment);
}
