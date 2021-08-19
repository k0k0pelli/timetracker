package id.meier.timetracking.application.services.comparator;



import id.meier.timetracking.domain.Assignment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component("assignmentStartDateTimeComparator2")
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
