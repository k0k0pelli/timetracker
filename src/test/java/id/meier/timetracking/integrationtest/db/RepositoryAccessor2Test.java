package id.meier.timetracking.integrationtest.db;



import id.meier.timetracking.application.port.in.assignmentmangement.commands.SaveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SelectAssignmentCommand;
import id.meier.timetracking.application.services.ManagementAssignmentService;
import id.meier.timetracking.domain.Assignment;
import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.AllOf.allOf;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(
  locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RepositoryAccessor2Test {
		
	@Autowired
	private ManagementAssignmentService assignmentService;


	
	@BeforeEach
	public void setup() {

	}
	
	@Test
	public void test() {
		Project project = new Project();
		project.setName("test1Project");
		Task task = new Task();
		task.setName("task1");
		//project.getTasks().add(task);
		Phase phase = new Phase();
		phase.setName("phase1");
		phase.getTasks().add(task);
		project.getPhases().add(phase);
		Assignment a = new Assignment();
		a.setStartDate(LocalDate.now());
		a.setStartTime(LocalTime.now());
		a.setEndDate(LocalDate.now());
		a.setEndTime(LocalTime.now());
		a.setDescription("Description 1");
		a.setProject(project);
		a.setPhase(phase);
		a.setTask(task);

		assignmentService.saveAssignment(SaveAssignmentCommand.of(a));

		List<Assignment> assignments = assignmentService.selectAssignments(SelectAssignmentCommand.builder().build());
		assertThat(assignments, hasSize(1));
		assertThat(assignments, hasItem(
				allOf(
					hasProperty("project", equalTo(project)),
					hasProperty("phase", equalTo(phase)),
					hasProperty("task", equalTo(task))
					)
				)
		);
	}

}
