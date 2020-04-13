package id.meier.timetracking.reporting.generator;

import id.meier.timetracking.model.Assignment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class AssignmentStartDateTimeComparator extends AssignmentDateTimeComparator {
    @Override
    protected LocalTime getTime(Assignment a) {
        return a.getStartTime();
    }

    @Override
    protected LocalDate getDate(Assignment a) {
        return a.getStartDate();
    }
}
