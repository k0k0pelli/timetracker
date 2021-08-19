package id.meier.timetracking.application.services.comparator;


import id.meier.timetracking.domain.Assignment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component("assignmentEndDateTimeComparator2")
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
