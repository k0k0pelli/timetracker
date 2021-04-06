package id.meier.timetracking.db.repository;

import id.meier.timetracking.db.entity.AssignmentEntity;
import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.ProjectEntity;
import id.meier.timetracking.db.entity.TaskEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AccountRepositoryCustom {
	List<AssignmentEntity> selectAssignments(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime,
											 ProjectEntity project, PhaseEntity phase, TaskEntity task);

	List<AssignmentEntity> selectAssignmentsStartDateTimeAfterGivenDateTime(LocalDate date, LocalTime time);
}
