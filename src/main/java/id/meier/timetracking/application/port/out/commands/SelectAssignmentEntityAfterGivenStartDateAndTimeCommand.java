package id.meier.timetracking.application.port.out.commands;

import java.time.LocalDate;
import java.time.LocalTime;

public class SelectAssignmentEntityAfterGivenStartDateAndTimeCommand {
    private LocalDate date;
    private LocalTime time;

    public SelectAssignmentEntityAfterGivenStartDateAndTimeCommand(LocalDate date, LocalTime time){
        this.date = date;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public static SelectAssignmentEntityAfterGivenStartDateAndTimeCommand of(LocalDate afterDate, LocalTime afterTime) {
        return new SelectAssignmentEntityAfterGivenStartDateAndTimeCommand(afterDate, afterTime);
    }

}
