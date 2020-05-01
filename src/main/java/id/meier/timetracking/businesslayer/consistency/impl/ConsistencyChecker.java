package id.meier.timetracking.businesslayer.consistency.impl;

import id.meier.timetracking.businesslayer.consistency.IConsistencyChecker;
import id.meier.timetracking.businesslayer.consistency.IConsistencyMessage;
import id.meier.timetracking.businesslayer.consistency.IConsistencyRule;
import id.meier.timetracking.businesslayer.consistency.impl.rules.DescriptionCheckRule;
import id.meier.timetracking.businesslayer.consistency.impl.rules.EndDateTimeCheckRule;
import id.meier.timetracking.businesslayer.consistency.impl.rules.ProjectPhaseTaskCheckRule;
import id.meier.timetracking.businesslayer.consistency.impl.rules.StartDateTimeCheckRule;
import id.meier.timetracking.model.Assignment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsistencyChecker implements IConsistencyChecker {
    private final List<IConsistencyRule> rules;
    public ConsistencyChecker(StartDateTimeCheckRule startCheck, EndDateTimeCheckRule endCheck,
                              ProjectPhaseTaskCheckRule projectPhaseTaskCheckRule, DescriptionCheckRule descriptionCheckRule) {
        rules = new ArrayList<>();
        registerConsistencyRule(startCheck);
        registerConsistencyRule(endCheck);
        registerConsistencyRule(projectPhaseTaskCheckRule);
        registerConsistencyRule(descriptionCheckRule);
    }

    private void registerConsistencyRule(IConsistencyRule rule) {
        rules.add(rule);
    }

    @Override
    public List<IConsistencyMessage> checkConsistency(Assignment assignment) {
        List<IConsistencyMessage> messages = new ArrayList<>();
        for (IConsistencyRule r : rules) {
            messages.addAll(r.checkConsistency(assignment));
        }
        return messages;
    }

}
