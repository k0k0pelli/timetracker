package id.meier.timetracking.businesslayer.consistency;

import id.meier.timetracking.db.entity.AssignmentEntity;

import java.util.List;

public interface IConsistencyChecker {
    List<IConsistencyMessage> checkConsistency(AssignmentEntity assignment);
}
