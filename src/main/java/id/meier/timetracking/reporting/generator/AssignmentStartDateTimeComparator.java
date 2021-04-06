package id.meier.timetracking.reporting.generator;

import id.meier.timetracking.db.entity.AssignmentEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class AssignmentStartDateTimeComparator extends AssignmentDateTimeComparator {
    @Override
    protected LocalTime getTime(AssignmentEntity a) {
        return a.getStartTime();
    }

    @Override
    protected LocalDate getDate(AssignmentEntity a) {
        return a.getStartDate();
    }
}
