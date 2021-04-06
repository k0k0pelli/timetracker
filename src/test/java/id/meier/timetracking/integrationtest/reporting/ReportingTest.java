package id.meier.timetracking.integrationtest.reporting;

import id.meier.timetracking.TestBase;
import id.meier.timetracking.db.entity.AssignmentEntity;
import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.ProjectEntity;
import id.meier.timetracking.db.entity.TaskEntity;
import id.meier.timetracking.reporting.generator.ReportGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class ReportingTest extends TestBase {
	@Autowired
	private ReportGenerator reportGenerator;
	
	private long nextId = 0;
	
	@Test
	public void testReporting() {
		AssignmentEntity a = new AssignmentEntity();
		String  s = Optional.of(a)
		         .map(AssignmentEntity::getProject)
		         .map(ProjectEntity::getName)
		         .orElse(null);
		reportGenerator.generateReport(getTestAssignments(), "/id/meier/timetracking/reports/TRGroupedByProjectPhaseTask.jasper", 
				new HashMap<>(), "./test.pdf");
	}
	
	private List<AssignmentEntity> getTestAssignments() {
		List<AssignmentEntity> list = new ArrayList<>();
		ProjectEntity project1 = createProject("Project a", "Project a description", nextId());
		ProjectEntity project2 = createProject("Project b", "Project b description", nextId());
		ProjectEntity project3 = createProject("Project c", "Project c description", nextId());
		ProjectEntity project4 = createProject("Project d", "Project d description", nextId());
		
		PhaseEntity phase1 = createPhase("Phase 1", "Phase 1 Description", nextId());
		PhaseEntity phase2 = createPhase("Phase 2", "Phase 2 Description", nextId());
		PhaseEntity phase3 = createPhase("Phase 3", "Phase 3 Description", nextId());
		PhaseEntity phase4 = createPhase("Phase 4", "Phase 4 Description", nextId());
		
		TaskEntity task1 = createTask("Task 1", "Task 1 Description", nextId());
		TaskEntity task2 = createTask("Task 2", "Task 2 Description", nextId());
		TaskEntity task3 = createTask("Task 3", "Task 3 Description", nextId());
		TaskEntity task4 = createTask("Task 4", "Task 4 Description", nextId());
		
		AssignmentEntity a1 = createAssignment(getDate(1), getTime(10,0), getDate(1), getTime(11,10),
				                         "description a1", project1, phase1, task1, nextId());
		AssignmentEntity a12 = createAssignment(getDate(2), getTime(10,0), getDate(2), getTime(11,10),
                "description a1", project1, phase1, task1, nextId());
		AssignmentEntity a13 = createAssignment(getDate(3), getTime(10,0), getDate(3), getTime(11,10),
                "description a1", project1, phase1, task1, nextId());
		AssignmentEntity a2 = createAssignment(getDate(2), getTime(9,0), getDate(2), getTime(13,20), "description a2", project2, phase2, task2, nextId());
		AssignmentEntity a3 = createAssignment(getDate(3), getTime(8,0), getDate(3), getTime(11,20), "description a3", project3, phase3, task3, nextId());
		AssignmentEntity a4 = createAssignment(getDate(4), getTime(8,0), getDate(4), getTime(11,20), "description a4", project4, phase4, task4, nextId());
		AssignmentEntity a5 = createAssignment(getDate(5), getTime(8,0), getDate(5), getTime(11,20), "description a4", project4, phase4, task4, nextId());
		AssignmentEntity a6 = createAssignment(getDate(6), getTime(8,0), getDate(6), getTime(11,20), "description a4", project4, phase4, task4, nextId());
		
		list.add(a1);
		list.add(a12);
		list.add(a13);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		list.add(a5);
		list.add(a6);
		return list;
	}

	private Long nextId() {
		return nextId++;
	}

}
