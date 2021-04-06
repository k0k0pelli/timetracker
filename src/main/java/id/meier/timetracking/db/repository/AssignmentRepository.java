package id.meier.timetracking.db.repository;

import id.meier.timetracking.db.entity.AssignmentEntity;
import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.ProjectEntity;
import id.meier.timetracking.db.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long>, AccountRepositoryCustom {

	List<AssignmentEntity> findByEndDateIsNullOrEndTimeIsNull();
	
	 @Query("SELECT " +
	           "    new id.meier.timetracking.db.repository.AssignmentRefHitParadeProjectPhaseTask(a.project, COUNT(a.project) as projectCount, " +
	           "    a.phase, COUNT(a.phase) as phaseCount, " +
	           "    a.task, COUNT(a.task) as taskCount) " +
	           "FROM " +
	           "    Assignment a " +
	           "GROUP BY " +
	           "    a.project, " +
	           "    a.phase, " +
	           "    a.task " +
	           "ORDER BY projectCount, phaseCount, taskCount desc")
	 List<AssignmentRefHitParadeProjectPhaseTask> findTopReferredProjectPhaseAndTasks();
	 
	 List<AssignmentEntity> findByProject(ProjectEntity project);
	 
	 List<AssignmentEntity> findByPhase(PhaseEntity phase);
	 
	 List<AssignmentEntity> findByTask(TaskEntity task);

	 List<AssignmentEntity> findAssignmentByCloneableTemplateName(String templateName);
}
