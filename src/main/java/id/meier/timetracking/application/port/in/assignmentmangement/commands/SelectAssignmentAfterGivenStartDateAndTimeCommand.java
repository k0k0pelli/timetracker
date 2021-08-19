package id.meier.timetracking.application.port.in.assignmentmangement.commands;

import java.time.LocalDate;
import java.time.LocalTime;

public class SelectAssignmentAfterGivenStartDateAndTimeCommand {
    private LocalDate date;
    private LocalTime time;

    public SelectAssignmentAfterGivenStartDateAndTimeCommand(LocalDate date, LocalTime time){
        this.date = date;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public static SelectAssignmentAfterGivenStartDateAndTimeCommand of(LocalDate afterDate, LocalTime afterTime) {
        return new SelectAssignmentAfterGivenStartDateAndTimeCommand(afterDate, afterTime);
    }

}
