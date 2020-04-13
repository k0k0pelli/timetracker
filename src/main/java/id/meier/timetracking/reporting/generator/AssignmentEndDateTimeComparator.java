package id.meier.timetracking.reporting.generator;

import id.meier.timetracking.model.Assignment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class AssignmentEndDateTimeComparator extends AssignmentDateTimeComparator {
    @Override
    protected LocalTime getTime(Assignment a) {
        return a.getEndTime();
    }

    @Override
    protected LocalDate getDate(Assignment a) {
        return a.getEndDate();
    }
}
