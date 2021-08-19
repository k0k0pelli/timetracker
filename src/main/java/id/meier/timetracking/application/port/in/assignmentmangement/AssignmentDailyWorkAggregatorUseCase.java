package id.meier.timetracking.application.port.in.assignmentmangement;



import id.meier.timetracking.domain.Assignment;
import id.meier.timetracking.domain.DailyWorkingPeriod;

import java.time.Duration;
import java.util.List;

public interface AssignmentDailyWorkAggregatorUseCase {
    List<DailyWorkingPeriod> aggregateAssignments(List<Assignment> assignments, Duration durationThreshHold);
}
