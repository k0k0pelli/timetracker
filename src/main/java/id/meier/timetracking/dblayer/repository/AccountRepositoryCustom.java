package id.meier.timetracking.dblayer.repository;

import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AccountRepositoryCustom {
	List<Assignment> selectAssignments(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime,
			Project project, Phase phase, Task task);

	List<Assignment> selectAssignmentsStartDateTimeAfterGivenDateTime(LocalDate date, LocalTime time);
}
