package id.meier.timetracking.application.services;

import id.meier.timetracking.application.port.in.assignmentmangement.ConsistencyCheckUseCase;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IConsistencyMessage;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IConsistencyRule;
import id.meier.timetracking.application.services.consistency.rules.DescriptionCheckRule;
import id.meier.timetracking.application.services.consistency.rules.EndDateTimeCheckRule;
import id.meier.timetracking.application.services.consistency.rules.ProjectPhaseTaskCheckRule;
import id.meier.timetracking.application.services.consistency.rules.StartDateTimeCheckRule;

import id.meier.timetracking.domain.Assignment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsistencyChecker implements ConsistencyCheckUseCase {
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
