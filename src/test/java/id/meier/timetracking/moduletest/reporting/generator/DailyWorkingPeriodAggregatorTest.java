package id.meier.timetracking.moduletest.reporting.generator;

import id.meier.timetracking.model.*;
import id.meier.timetracking.reporting.generator.AssignmentStartDateTimeComparator;
import id.meier.timetracking.reporting.generator.DailyWorkingPeriodAggregator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class DailyWorkingPeriodAggregatorTest {

    private DailyWorkingPeriodAggregator testee;

    @BeforeEach
    public void setup() {
        testee = new DailyWorkingPeriodAggregator(new AssignmentStartDateTimeComparator());
    }

    @Test
    public void testWorkingBlockAggregator() {
        Duration duration = Duration.ofMinutes(1);

        List<Assignment> assignments = getAssignments();

        List<DailyWorkingPeriod> workingBlocks = testee.aggregateAssignments(assignments, duration);
        assertThat(workingBlocks, hasSize(6));
    }

    private List<Assignment> getAssignments() {
        List<Assignment> assignments = new ArrayList<>();
        LocalDate d0 = getDate(12);
        LocalDate d1 = getDate(13);
        LocalDate d2 = getDate(14);
        LocalTime t1 = getTime(13,30,0);
        LocalTime t2 = getTime(14,30,0);
        LocalTime t3 = getTime(15,0,0);
        LocalTime t4 = getTime(15,15,0);
        LocalTime t5 = getTime(17,30,0);
        LocalTime t6 = getTime(8,0,0);
        LocalTime t7 = getTime(9,0,0);
        LocalTime t8 = getTime(9,15,0);
        LocalTime t9 = getTime(9,15,45);
        LocalTime t10 = getTime(12,0,0);

        assignments.add(getAssignment(null,null, d0, t5));
        assignments.add(getAssignment(d0,t1, null, null));
        assignments.add(getAssignment(d1,t1, d1, t2));
        assignments.add(getAssignment(d1,t2, d1, t3));
        assignments.add(getAssignment(d1,t4, d1, t5));
        assignments.add(getAssignment(d2,t6, d2, t7));
        assignments.add(getAssignment(d2,t8, d2, t9));
        assignments.add(getAssignment(d2,t9, d2, t10));
        return assignments;

    }

    private LocalDate getDate(int day) {
        return LocalDate.of(2019, 2, day);
    }

    private LocalTime getTime(int hour, int minute, int seconds) {
        return LocalTime.of(hour, minute, seconds);
    }

    private Assignment getAssignment(LocalDate sDate, LocalTime sTime, LocalDate eDate, LocalTime eTime) {
        Project project = new Project();
        Phase phase = new Phase();
        Task task = new Task();
        Assignment a = new Assignment();
        a.setProject(project);
        a.setPhase(phase);
        a.setTask(task);
        a.setStartDate(sDate);
        a.setStartTime(sTime);
        a.setEndDate(eDate);
        a.setEndTime(eTime);
        return a;
    }

}
