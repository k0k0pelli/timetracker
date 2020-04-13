package id.meier.timetracking;

import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalTime;

public class TestBase {

    protected LocalDate getDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    protected LocalTime getTime(int hours, int minutes, int seconds) {
        return LocalTime.of(hours, minutes, seconds);
    }

    protected LocalDate getDate(int day) {
        return LocalDate.of(2019, 1, day);
    }

    protected LocalTime getTime(int hours, int seconds) {
        return LocalTime.of(hours, 0, seconds);
    }

    protected Project createProject(String name, String description ) {
        return createProject(name, description, null);
    }

    protected Project createProject(String name, String description, Long nextId ) {
        Project p = new Project();
        p.setDescription(description);
        p.setName(name);
        p.setId(nextId++);
        try {
            BeanUtils.setProperty(new Object(), "bla", 12);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return p;
    }

    protected Phase createPhase(String name, String description) {
        return createPhase(name, description, null);
    }

    protected Phase createPhase(String name, String description, Long nextId) {
        Phase p = new Phase();
        p.setDescription(description);
        p.setName(name);
        p.setId(nextId++);
        return p;
    }

    protected Task createTask(String name, String description) {
        return createTask(name, description, null);
    }

    protected Task createTask(String name, String description, Long nextId) {
        Task t = new Task();
        t.setDescription(description);
        t.setName(name);
        t.setId(nextId++);
        return t;
    }

    protected Assignment createAssignment(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime,
                                          String description, Project project, Phase phase, Task task) {
        return createAssignment(startDate, startTime, endDate, endTime, description, project, phase, task, null);
    }

    protected Assignment createAssignment(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime,
                                        String description, Project project, Phase phase, Task task, Long nextId) {
        Assignment a = new Assignment();
        a.setStartDate(startDate);
        a.setStartTime(startTime);
        a.setEndDate(endDate);
        a.setEndTime(endTime);
        a.setDescription(description);
        a.setProject(project);
        a.setPhase(phase);
        a.setTask(task);
        a.setId(nextId++);
        return a;
    }
}
