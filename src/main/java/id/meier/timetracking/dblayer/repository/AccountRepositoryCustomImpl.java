package id.meier.timetracking.dblayer.repository;

import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepositoryCustomImpl implements AccountRepositoryCustom {
	@PersistenceContext
    private EntityManager em;

	@Override
	public List<Assignment> selectAssignments(LocalDate startDate, LocalTime startTime, LocalDate endDate,
			LocalTime endTime, Project project, Phase phase, Task task) {
		 	CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Assignment> q = cb.createQuery(Assignment.class);
		    Root<Assignment> user = q.from(Assignment.class);

		    Path<LocalDate> startDatePath = user.get("startDate");
		    Path<LocalTime> startTimePath = user.get("startTime");
		    Path<LocalDate> endDatePath = user.get("endDate");
		    Path<LocalTime> endTimePath = user.get("endTime");
		    Path<Project> projectPath = user.get("project");
		    Path<Phase> phasePath = user.get("phase");
		    Path<Task> taskPath = user.get("task");
		    List<Predicate> predicates = new ArrayList<>();
		    if (startDate != null) {
		    	predicates.add(cb.greaterThanOrEqualTo(startDatePath, cb.literal(startDate)));
		    }
		    if (startTime != null) {
		    	predicates.add(cb.greaterThanOrEqualTo(startTimePath, cb.literal(startTime)));
		    }
		    if (endDate != null) {
		    	predicates.add(cb.or(cb.lessThanOrEqualTo(endDatePath, cb.literal(endDate)),
						cb.isNull(endDatePath)));
		    }
		    if (endTime != null) {
		    	predicates.add(cb.or(
		    			cb.lessThanOrEqualTo(endTimePath, cb.literal(endTime)),
						cb.isNull(endTimePath)));
		    }
		    if (project != null) {
		    	predicates.add(cb.equal(projectPath,cb.literal(project)));
		    }
		    if (phase != null) {
		    	predicates.add(cb.equal(phasePath,cb.literal(phase)));
		    }
		    if (task != null) {
		    	predicates.add(cb.equal(taskPath,cb.literal(task)));
		    }

		q.where(cb.and(predicates.toArray(new Predicate[0])));

		return em.createQuery(q).getResultList();
	}

	public List<Assignment> selectAssignmentsStartDateTimeAfterGivenDateTime(LocalDate date, LocalTime time) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Assignment> q = cb.createQuery(Assignment.class);
			Root<Assignment> user = q.from(Assignment.class);

			Path<LocalDate> startDatePath = user.get("startDate");
			Path<LocalTime> startTimePath = user.get("startTime");
			Path<LocalDate> endDatePath = user.get("endDate");
			Path<LocalTime> endTimePath = user.get("endTime");
			List<Predicate> predicates = new ArrayList<>();
			if (date != null) {
				predicates.add(cb.lessThanOrEqualTo(startDatePath, cb.literal(date)));
				predicates.add(cb.greaterThanOrEqualTo(endDatePath, cb.literal(date)));
			}
			if (time != null) {
				predicates.add(cb.lessThan(startTimePath, cb.literal(time)));
				predicates.add(cb.greaterThan(endTimePath, cb.literal(time)));
			}

			q.where(cb.and(predicates.toArray(new Predicate[0])));

			return em.createQuery(q).getResultList();
	}
}
