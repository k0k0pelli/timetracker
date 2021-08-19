package id.meier.timetracking.adapter.out.persistence.repositories;

import id.meier.timetracking.adapter.out.persistence.entities.AssignmentEntity;
import id.meier.timetracking.adapter.out.persistence.entities.PhaseEntity;
import id.meier.timetracking.adapter.out.persistence.entities.ProjectEntity;
import id.meier.timetracking.adapter.out.persistence.entities.TaskEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AssignmentRepositorySelection {

	List<AssignmentEntity> selectAssignments(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime,
											 ProjectEntity project, PhaseEntity phase, TaskEntity task);

	List<AssignmentEntity> selectAssignmentsStartDateTimeAfterGivenDateTime(LocalDate date, LocalTime time);
}
