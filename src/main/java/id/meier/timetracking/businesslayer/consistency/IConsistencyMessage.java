package id.meier.timetracking.businesslayer.consistency;

import id.meier.timetracking.businesslayer.consistency.impl.AdditionalMessageData;
import id.meier.timetracking.model.Assignment;

import java.util.List;

public interface IConsistencyMessage  {
    Assignment getAssignment();
    List<ConsistencyProblem> getProblems();
    List<AdditionalMessageData<?>> getMessageData();
    void addMessageData(AdditionalMessageData<?> message);
    void addMessageData(List<AdditionalMessageData<?>> messages);
}
