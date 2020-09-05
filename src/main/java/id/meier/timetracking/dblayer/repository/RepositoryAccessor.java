package id.meier.timetracking.dblayer.repository;

import id.meier.timetracking.businesslayer.commands.Command;
import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RepositoryAccessor {
	private final ProjectRepository projectRepo;
	private final PhaseRepository phaseRepo;
	private final TaskRepository taskRepo;
	private final AssignmentRepository assignmentRepo;
	private final Map<Class<?>, JpaRepository<?,Long>> repositories;
	

	public RepositoryAccessor(ProjectRepository projectRepo, PhaseRepository phaseRepo, TaskRepository taskRepo, AssignmentRepository assignmentRepo) {
		this.projectRepo = projectRepo;
		this.phaseRepo = phaseRepo;
		this.taskRepo = taskRepo;
		this.assignmentRepo = assignmentRepo;
		repositories = new HashMap<>();
		repositories.put(Project.class, projectRepo);
		repositories.put(Phase.class, phaseRepo);
		repositories.put(Task.class, taskRepo);
		repositories.put(Assignment.class, assignmentRepo);
	}
	
	public void executeCommands(List<Command> commands) {
		for (Command c : commands) {
			c.setRepoAccessor(this);
			c.execute();
		}
	}

	public <T> T findById(Long id, Class<T> clazz) {
		JpaRepository<T,Long> repo = this.getRepo(clazz);
		return repo.findById(id).orElse(null);
	}
	
	public List<Project> findProjectByNameStartsWithIgnoreCase(String name) {
		return this.projectRepo.findByNameStartsWithIgnoreCase(name);
	}
	
	 public List<Task> findTaskByNameStartsWithIgnoreCase(String name) {
		 return this.taskRepo.findByNameStartsWithIgnoreCase(name);
	 }

	 public List<Project> findProjectByPhase(Phase phase) {
		return this.projectRepo.findByPhasesIn(Collections.singletonList(phase));
	 }

	public List<Phase> findProjectByPhase(Task task) {
		return this.phaseRepo.findByTasksIn(Collections.singletonList(task));
	}

	 public List<Phase> findPhasesByNameStartsWithIgnoreCase(String name) {
		 return this.phaseRepo.findByNameStartsWithIgnoreCase(name);
	 }
	
	 public List<Assignment> findAssignmentByEndDateIsNullOrEndTimeIsNull() {
		 return assignmentRepo.findByEndDateIsNullOrEndTimeIsNull();
	 }
	
	 public List<AssignmentRefHitParadeProjectPhaseTask> findTopReferredProjectPhaseAndTasks() {
		 return assignmentRepo.findTopReferredProjectPhaseAndTasks();
	 }
	 
	 public List<Assignment> selectAssignments(LocalDate startDate, LocalTime startTime, LocalDate endDate, 
			 LocalTime endTime, Project project, Phase phase, Task task) {
		 return assignmentRepo.selectAssignments(startDate, startTime, endDate, endTime, project, phase, task);
	 }

	public List<Assignment> selectAssignmentsStartDateTimeAfterGivenDateTime(LocalDate date, LocalTime time) {
		return assignmentRepo.selectAssignmentsStartDateTimeAfterGivenDateTime(date, time);
	}

	 public <T> T save(T element) {
		@SuppressWarnings("unchecked")
		JpaRepository<T,Long> repo = (JpaRepository<T, Long>) this.getRepo(element.getClass());
		return repo.save(element);
	 }
	 
	 public <T> void delete(T element) {
		@SuppressWarnings("unchecked")
		JpaRepository<T,Long> repo = (JpaRepository<T, Long>) this.getRepo(element.getClass());
		repo.delete(element);
	}
	 
	 public <T> List<T> findAll(Class<T> clazz) {		
		JpaRepository<T,Long> repo = this.getRepo(clazz);
		return repo.findAll();
	}

	@SuppressWarnings("unchecked")
	private <T> JpaRepository<T, Long> getRepo(Class<T> clazz) {
		return (JpaRepository<T, Long>) repositories.get(clazz);
	}
	 
	 public ProjectRepository getProjectRepo() {
		return projectRepo;
	 }

	public PhaseRepository getPhaseRepo() {
		return phaseRepo;
	}

	public TaskRepository getTaskRepo() {
		return taskRepo;
	}

	public AssignmentRepository getAssignmentRepo() {
		return assignmentRepo;
	}
	
	public List<Assignment> findAssignmentByProject(Project project) {
		return assignmentRepo.findByProject(project);
	}
	 
	public List<Assignment> findAssignmentByPhase(Phase phase) {
		return assignmentRepo.findByPhase(phase);
	}
	 
	public List<Assignment> findAssignmentByTask(Task task) {
	    return assignmentRepo.findByTask(task);
	}
	 
}
