package id.meier.timetracking.businesslayer.consistency.impl;

import id.meier.timetracking.businesslayer.consistency.ConsistencyProblem;
import id.meier.timetracking.businesslayer.consistency.IConsistencyMessage;
import id.meier.timetracking.model.Assignment;

import java.util.ArrayList;
import java.util.List;


public class ConsistencyMessage implements IConsistencyMessage {
    private final Assignment assignement;
    private final List<ConsistencyProblem> problems;
    private final List<AdditionalMessageData<?>> additionalMessageData;

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
    public List<AdditionalMessageData<?>> getMessageData() {
        return additionalMessageData;
    }

    @Override
    public void addMessageData(AdditionalMessageData<?> message) {
        this.additionalMessageData.add(message);
    }

    @Override
    public void addMessageData(List<AdditionalMessageData<?>> messages) {
        this.additionalMessageData.addAll(messages);
    }
}
