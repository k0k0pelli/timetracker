package id.meier.timetracking.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class DailyWorkingPeriod {
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private double durationIndustrialHours;

    public DailyWorkingPeriod(LocalDate startDate,
                              LocalTime startTime,
                              LocalDate endDate,
                              LocalTime endTime,
                              double durationInHours) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.durationIndustrialHours = durationInHours;
    }

    public DailyWorkingPeriod() {
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getDurationIndustrialHours() {
        return durationIndustrialHours;
    }

    public void setDurationIndustrialHours(double durationIndustrialHours) {
        this.durationIndustrialHours = durationIndustrialHours;
    }

}
