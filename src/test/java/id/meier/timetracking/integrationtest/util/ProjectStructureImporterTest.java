package id.meier.timetracking.integrationtest.util;


import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.dblayer.repository.RepositoryAccessor;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;
import id.meier.timetracking.util.ProjectStructureImporter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(
  locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProjectStructureImporterTest {
		
	@Autowired
	private RepositoryAccessor accessor;

	private CommandsCollector collector;


	private ProjectStructureImporter testee;

	@BeforeEach
	public void setup() {
		testee = new ProjectStructureImporter(accessor);
	}
	
	@Test
	public void testProjectsImport() {
		// given
		testee.prepareImport("./src/test/resources/id.meier.timetracking.integrationtest.util/projects.csv",
				"./src/test/resources/id.meier.timetracking.integrationtest.util/phases.csv",
				"./src/test/resources/id.meier.timetracking.integrationtest.util/tasks.csv",
				"./src/test/resources/id.meier.timetracking.integrationtest.util/assignments.csv");

		// when
		testee.commitImportedProjectMetaData();

		// then
		List<Project> projects = accessor.findAll(Project.class);
		Assertions.assertThat(projects).hasSize(3);
		Assertions.assertThat(projects).element(0).hasFieldOrPropertyWithValue("name","p1");
		Assertions.assertThat(projects).element(1).hasFieldOrPropertyWithValue("name","p2");
		Assertions.assertThat(projects).element(2).hasFieldOrPropertyWithValue("name","p3");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPhaseImport() {
		// given
		testee.prepareImport("./src/test/resources/id.meier.timetracking.integrationtest.util/projects.csv",
				"./src/test/resources/id.meier.timetracking.integrationtest.util/phases.csv",
				"./src/test/resources/id.meier.timetracking.integrationtest.util/tasks.csv",
				"./src/test/resources/id.meier.timetracking.integrationtest.util/assignments.csv");

		// when
		testee.commitImportedProjectMetaData();

		// then
		List<Project> projects = accessor.findAll(Project.class);
		Assertions.assertThat(projects)
				.element(0)
				.satisfies(p -> Assertions.assertThat(p.getPhases()).hasSize(2));
		Assertions.assertThat(projects).flatExtracting(Project::getPhases).flatExtracting(Phase::getName)
				.containsExactly("phase 1", "phase 2");
	}

	@Test
	public void testTaskImport() {
		// given
		testee.prepareImport("./src/test/resources/id.meier.timetracking.integrationtest.util/projects.csv",
				"./src/test/resources/id.meier.timetracking.integrationtest.util/phases.csv",
				"./src/test/resources/id.meier.timetracking.integrationtest.util/tasks.csv",
				"./src/test/resources/id.meier.timetracking.integrationtest.util/assignments.csv");

		// when
		testee.commitImportedProjectMetaData();

		// then
		List<Project> projects = accessor.findAll(Project.class);
		Assertions.assertThat(projects)
				.element(0)
				.satisfies(p -> Assertions.assertThat(p.getPhases().get(0).getTasks())
					.flatExtracting(Task::getName)
					.containsExactly("task 1", "task 2", "task 3")
				);

	}

}
