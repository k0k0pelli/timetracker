package id.meier.timetracking;

import id.meier.timetracking.db.entity.AssignmentEntity;
import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.ProjectEntity;
import id.meier.timetracking.db.entity.TaskEntity;
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

    protected ProjectEntity createProject(String name, String description ) {
        return createProject(name, description, null);
    }

    protected ProjectEntity createProject(String name, String description, Long nextId ) {
        ProjectEntity p = new ProjectEntity();
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

    protected PhaseEntity createPhase(String name, String description) {
        return createPhase(name, description, null);
    }

    protected PhaseEntity createPhase(String name, String description, Long nextId) {
        PhaseEntity p = new PhaseEntity();
        p.setDescription(description);
        p.setName(name);
        p.setId(nextId++);
        return p;
    }

    protected TaskEntity createTask(String name, String description) {
        return createTask(name, description, null);
    }

    protected TaskEntity createTask(String name, String description, Long nextId) {
        TaskEntity t = new TaskEntity();
        t.setDescription(description);
        t.setName(name);
        t.setId(nextId++);
        return t;
    }

    protected AssignmentEntity createAssignment(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime,
                                                String description, ProjectEntity project, PhaseEntity phase, TaskEntity task) {
        return createAssignment(startDate, startTime, endDate, endTime, description, project, phase, task, null);
    }

    protected AssignmentEntity createAssignment(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime,
                                                String description, ProjectEntity project, PhaseEntity phase, TaskEntity task, Long nextId) {
        AssignmentEntity a = new AssignmentEntity();
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
