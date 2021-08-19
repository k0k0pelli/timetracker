package id.meier.timetracking.adapter.out.persistence.repositories;

import id.meier.timetracking.adapter.out.persistence.entities.*;
import id.meier.timetracking.domain.AssignmentRefHitParadeProjectPhaseTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AssignmentRepository2 extends JpaRepository<AssignmentEntity, Long>, AssignmentRepositorySelection {

	List<AssignmentEntity> findByEndDateIsNullOrEndTimeIsNull();
	
	 @Query("SELECT " +
	           "    new id.meier.timetracking.adapter.out.persistence.entities.AssignmentRefHitParadeProjectPhaseTaskEntity(" +
			   "        a.project, COUNT(a.project) as projectCount, " +
	           "        a.phase, COUNT(a.phase) as phaseCount, " +
	           "        a.task, COUNT(a.task) as taskCount) " +
	           "FROM " +
	           "    Assignment2 a " +
	           "GROUP BY " +
	           "    a.project, " +
	           "    a.phase, " +
	           "    a.task " +
	           "ORDER BY projectCount, phaseCount, taskCount desc")
	 List<AssignmentRefHitParadeProjectPhaseTaskEntity> findTopReferredProjectPhaseAndTasks();
	 
	 List<AssignmentEntity> findByProject(ProjectEntity project);
	 
	 List<AssignmentEntity> findByPhase(PhaseEntity phase);
	 
	 List<AssignmentEntity> findByTask(TaskEntity task);

	 List<AssignmentEntity> findAssignmentByCloneableTemplateName(String templateName);
}
