package id.meier.timetracking.integrationtest.reporting;

import id.meier.timetracking.TestBase;
import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;
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
		Assignment a = new Assignment();
		String  s = Optional.of(a)
		         .map(Assignment::getProject)
		         .map(Project::getName)
		         .orElse(null);
		reportGenerator.generateReport(getTestAssignments(), "/id/meier/timetracking/reports/TRGroupedByProjectPhaseTask.jasper", 
				new HashMap<>(), "./test.pdf");
	}
	
	private List<Assignment> getTestAssignments() {
		List<Assignment> list = new ArrayList<>();
		Project project1 = createProject("Project a", "Project a description", nextId());
		Project project2 = createProject("Project b", "Project b description", nextId());
		Project project3 = createProject("Project c", "Project c description", nextId());
		Project project4 = createProject("Project d", "Project d description", nextId());
		
		Phase phase1 = createPhase("Phase 1", "Phase 1 Description", nextId());
		Phase phase2 = createPhase("Phase 2", "Phase 2 Description", nextId());
		Phase phase3 = createPhase("Phase 3", "Phase 3 Description", nextId());
		Phase phase4 = createPhase("Phase 4", "Phase 4 Description", nextId());
		
		Task task1 = createTask("Task 1", "Task 1 Description", nextId());
		Task task2 = createTask("Task 2", "Task 2 Description", nextId());
		Task task3 = createTask("Task 3", "Task 3 Description", nextId());
		Task task4 = createTask("Task 4", "Task 4 Description", nextId());
		
		Assignment a1 = createAssignment(getDate(1), getTime(10,0), getDate(1), getTime(11,10),
				                         "description a1", project1, phase1, task1, nextId());
		Assignment a12 = createAssignment(getDate(2), getTime(10,0), getDate(2), getTime(11,10),
                "description a1", project1, phase1, task1, nextId());
		Assignment a13 = createAssignment(getDate(3), getTime(10,0), getDate(3), getTime(11,10),
                "description a1", project1, phase1, task1, nextId());
		Assignment a2 = createAssignment(getDate(2), getTime(9,0), getDate(2), getTime(13,20), "description a2", project2, phase2, task2, nextId());
		Assignment a3 = createAssignment(getDate(3), getTime(8,0), getDate(3), getTime(11,20), "description a3", project3, phase3, task3, nextId());
		Assignment a4 = createAssignment(getDate(4), getTime(8,0), getDate(4), getTime(11,20), "description a4", project4, phase4, task4, nextId());
		Assignment a5 = createAssignment(getDate(5), getTime(8,0), getDate(5), getTime(11,20), "description a4", project4, phase4, task4, nextId());
		Assignment a6 = createAssignment(getDate(6), getTime(8,0), getDate(6), getTime(11,20), "description a4", project4, phase4, task4, nextId());
		
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
