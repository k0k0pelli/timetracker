package id.meier.timetracking.businesslayer.consistency;

import id.meier.timetracking.db.entity.AssignmentEntity;

import java.util.List;

public interface IConsistencyRule {
    List<IConsistencyMessage> checkConsistency(AssignmentEntity assignment);
}
