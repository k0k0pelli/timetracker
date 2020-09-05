package id.meier.timetracking.dblayer.repository;

import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface AssignmentRepository extends JpaRepository<Assignment, Long>, AccountRepositoryCustom {

	List<Assignment> findByEndDateIsNullOrEndTimeIsNull();
	
	 @Query("SELECT " +
	           "    new id.meier.timetracking.dblayer.repository.AssignmentRefHitParadeProjectPhaseTask(a.project, COUNT(a.project) as projectCount, " +
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
	 
	 List<Assignment> findByProject(Project project);
	 
	 List<Assignment> findByPhase(Phase phase);
	 
	 List<Assignment> findByTask(Task task);

	 List<Assignment> findAssignmentByCloneableTemplateName(String templateName);
}
