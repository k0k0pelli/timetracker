package id.meier.timetracking.application.services;

import id.meier.timetracking.application.port.in.assignmentmangement.AssignmentDailyWorkAggregatorUseCase;

import id.meier.timetracking.application.services.comparator.AssignmentStartDateTimeComparator;
import id.meier.timetracking.domain.Assignment;
import id.meier.timetracking.domain.DailyWorkingPeriod;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AssignmentDailyWorkAggregatorService implements AssignmentDailyWorkAggregatorUseCase {
    private final  AssignmentStartDateTimeComparator comparator;
    public AssignmentDailyWorkAggregatorService(@Qualifier("assignmentStartDateTimeComparator2") AssignmentStartDateTimeComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public List<DailyWorkingPeriod> aggregateAssignments(List<Assignment> assignments, Duration durationThreshHold) {
        List<DailyWorkingPeriod> workingBlocks = new ArrayList<>();
        List<Assignment> sortedAssignments = new ArrayList<>(assignments);
        sortedAssignments.sort(comparator);
        LocalDateTime blockStartDateTime = null;
        LocalDateTime previousDateTime = null;
        LocalDateTime start;
        LocalDateTime end;
        for (Assignment a : sortedAssignments) {
            start = getStartDateTime(a);
            end = getEndDateTime(a);

            if (start != null && end != null) {
                if (blockStartDateTime == null) {
                    blockStartDateTime = start;
                }
                if (previousDateTime == null) {
                    previousDateTime = start;
                }
                if (isNewTimeBlock(previousDateTime, start, durationThreshHold)) {
                    DailyWorkingPeriod dwb = createDailyWorkingBlock(blockStartDateTime, previousDateTime);
                    workingBlocks.add(dwb); // store the old time block
                    previousDateTime = end; // the next previous date time is the current end date time
                    blockStartDateTime = start; // the current
                } else {
                    // we are still in the same time block
                    previousDateTime = end;
                }

            } else {
                DailyWorkingPeriod dwb = new DailyWorkingPeriod(a.getStartDate(), a.getStartTime(),
                        a.getEndDate(), a.getEndTime(), -1);
                workingBlocks.add(dwb);
            }
        }

        if (blockStartDateTime != null) {
            DailyWorkingPeriod dwb = createDailyWorkingBlock(blockStartDateTime, previousDateTime);
            workingBlocks.add(dwb);
        }
        return workingBlocks;
    }

    private boolean isNewTimeBlock(LocalDateTime previousDateTime, LocalDateTime start, Duration durationThreshHold) {
        Duration duration = Duration.between(previousDateTime, start);
        return duration.compareTo(durationThreshHold) > 0;
    }

    private DailyWorkingPeriod createDailyWorkingBlock(LocalDateTime blockStartDateTime,
                                                       LocalDateTime previousDateTime) {
        Duration startEndDiff = Duration.between(blockStartDateTime, previousDateTime);
        long seconds = startEndDiff.getSeconds();
        long hours = seconds / 3600;
        long remainingSecs = seconds % 3600;
        double timePeriod = (double)hours + (double)remainingSecs / 3600d;
        return new DailyWorkingPeriod(blockStartDateTime.toLocalDate(), blockStartDateTime.toLocalTime(),
                previousDateTime.toLocalDate(), previousDateTime.toLocalTime(), timePeriod);
    }

    private LocalDateTime getStartDateTime(Assignment a) {
        LocalDateTime start = null;
        if (a.getStartDate() != null && a.getStartTime() != null) {
            start = LocalDateTime.of(a.getStartDate(), a.getStartTime());
        }
        return start;
    }

    private LocalDateTime getEndDateTime(Assignment a) {
        LocalDateTime end = null;
        if (a.getEndDate() != null && a.getEndTime() != null) {
            end = LocalDateTime.of(a.getEndDate(), a.getEndTime());
        }
        return end;
    }
}
