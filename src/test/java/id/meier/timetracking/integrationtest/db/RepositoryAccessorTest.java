package id.meier.timetracking.integrationtest.db;


import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.TaskEntity;
import id.meier.timetracking.db.repository.RepositoryAccessor;
import id.meier.timetracking.db.entity.AssignmentEntity;
import id.meier.timetracking.db.entity.ProjectEntity;
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
public class RepositoryAccessorTest {
		
	@Autowired
	private RepositoryAccessor accessor;

	private CommandsCollector collector;
	
	@BeforeEach
	public void setup() {
		collector = new CommandsCollector();
	}
	
	@Test
	public void test() {
		ProjectEntity project = new ProjectEntity();
		project.setName("test1Project");
		TaskEntity task = new TaskEntity();
		task.setName("task1");
		//project.getTasks().add(task);
		PhaseEntity phase = new PhaseEntity();
		phase.setName("phase1");
		phase.getTasks().add(task);
		project.getPhases().add(phase);
		AssignmentEntity a = new AssignmentEntity();
		a.setStartDate(LocalDate.now());
		a.setStartTime(LocalTime.now());
		a.setEndDate(LocalDate.now());
		a.setEndTime(LocalTime.now());
		a.setDescription("Description 1");
		a.setProject(project);
		a.setPhase(phase);
		a.setTask(task);
		collector.save(task);
		collector.save(phase);
		collector.save(project);
		collector.save(a);
		collector.performPersistingOperations(accessor);

		List<AssignmentEntity> assignments = accessor.findAll(AssignmentEntity.class);
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
