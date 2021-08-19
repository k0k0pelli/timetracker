package id.meier.timetracking.application.services.consistency;

import id.meier.timetracking.application.port.in.assignmentmangement.consistency.ConsistencyProblem;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IAdditionalMessageData;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IConsistencyMessage;

import id.meier.timetracking.domain.Assignment;


import java.util.ArrayList;
import java.util.List;


public class ConsistencyMessage implements IConsistencyMessage {
    private final Assignment assignement;
    private final List<ConsistencyProblem> problems;
    private final List<IAdditionalMessageData<?>> additionalMessageData;

    public ConsistencyMessage(Assignment assignment) {
        this.assignement = assignment;
        problems = new ArrayList<>();
        additionalMessageData = new ArrayList<>();
    }

    public ConsistencyMessage addMessage(ConsistencyProblem problem) {
        this.problems.add(problem);
        return this;
    }

    @Override
    public Assignment getAssignment() {
        return assignement;
    }

    @Override
    public List<ConsistencyProblem> getProblems() {
        return problems;
    }

    @Override
    public List<IAdditionalMessageData<?>> getMessageData() {
        return additionalMessageData;
    }

    @Override
    public void addMessageData(IAdditionalMessageData<?> message) {
        this.additionalMessageData.add(message);
    }


    @Override
    public void addMessageData(List<IAdditionalMessageData<?>> messages) {
        this.additionalMessageData.addAll(messages);
    }
}
