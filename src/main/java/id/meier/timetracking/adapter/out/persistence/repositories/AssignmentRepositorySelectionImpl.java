package id.meier.timetracking.adapter.out.persistence.repositories;

import id.meier.timetracking.adapter.out.persistence.entities.AssignmentEntity;
import id.meier.timetracking.adapter.out.persistence.entities.PhaseEntity;
import id.meier.timetracking.adapter.out.persistence.entities.ProjectEntity;
import id.meier.timetracking.adapter.out.persistence.entities.TaskEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AssignmentRepositorySelectionImpl implements AssignmentRepositorySelection {
	@PersistenceContext
    private EntityManager em;

	@Override
	public List<AssignmentEntity> selectAssignments(LocalDate startDate, LocalTime startTime, LocalDate endDate,
													LocalTime endTime, ProjectEntity project, PhaseEntity phase, TaskEntity task) {
		 	CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<AssignmentEntity> q = cb.createQuery(AssignmentEntity.class);
		    Root<AssignmentEntity> assignmentQueryRoot = q.from(AssignmentEntity.class);

		    Path<LocalDate> startDatePath = assignmentQueryRoot.get("startDate");
		    Path<LocalTime> startTimePath = assignmentQueryRoot.get("startTime");
		    Path<LocalDate> endDatePath = assignmentQueryRoot.get("endDate");
		    Path<LocalTime> endTimePath = assignmentQueryRoot.get("endTime");
		    Path<ProjectEntity> projectPath = assignmentQueryRoot.get("project");
		    Path<PhaseEntity> phasePath = assignmentQueryRoot.get("phase");
		    Path<TaskEntity> taskPath = assignmentQueryRoot.get("task");
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

	public List<AssignmentEntity> selectAssignmentsStartDateTimeAfterGivenDateTime(LocalDate date, LocalTime time) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<AssignmentEntity> q = cb.createQuery(AssignmentEntity.class);
			Root<AssignmentEntity> user = q.from(AssignmentEntity.class);

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
