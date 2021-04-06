package id.meier.timetracking.businesslayer.consistency;

import id.meier.timetracking.businesslayer.consistency.impl.AdditionalMessageData;
import id.meier.timetracking.db.entity.AssignmentEntity;

import java.util.List;

public interface IConsistencyMessage  {
    AssignmentEntity getAssignment();
    List<ConsistencyProblem> getProblems();
    List<AdditionalMessageData<?>> getMessageData();
    void addMessageData(AdditionalMessageData<?> message);
    void addMessageData(List<AdditionalMessageData<?>> messages);
}
