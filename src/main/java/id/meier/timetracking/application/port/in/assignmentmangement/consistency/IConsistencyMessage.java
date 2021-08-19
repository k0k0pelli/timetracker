package id.meier.timetracking.application.port.in.assignmentmangement.consistency;


import id.meier.timetracking.domain.Assignment;

import java.util.List;

public interface IConsistencyMessage  {
    Assignment getAssignment();
    List<ConsistencyProblem> getProblems();
    List<IAdditionalMessageData<?>> getMessageData();
    void addMessageData(IAdditionalMessageData<?> message);
    void addMessageData(List<IAdditionalMessageData<?>> messages);
}
