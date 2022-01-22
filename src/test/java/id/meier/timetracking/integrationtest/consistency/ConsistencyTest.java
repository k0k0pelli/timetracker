/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package id.meier.timetracking.integrationtest.consistency;
import id.meier.timetracking.TestBase;
import id.meier.timetracking.application.port.in.assignmentmangement.ConsistencyCheckUseCase;
import id.meier.timetracking.application.port.in.assignmentmangement.commands.SaveAssignmentCommand;
import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IConsistencyMessage;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SavePhaseCommand;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SaveProjectCommand;
import id.meier.timetracking.application.port.in.structuremanagment.commands.SaveTaskCommand;
import id.meier.timetracking.application.services.ManageProjectStructureService;
import id.meier.timetracking.application.services.ManagementAssignmentService;
import id.meier.timetracking.domain.Assignment;
import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(
		locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ConsistencyTest extends TestBase {



	@Autowired
	private ManageProjectStructureService projectStructureService;
	@Autowired
    private ManagementAssignmentService assignmentService;



	@Autowired
	private ConsistencyCheckUseCase checker;


	@Test
	public void testNoConsistencyWarning() {
	    Assignment a = setupData();
        List<IConsistencyMessage> messages = checker.checkConsistency(a);

        assertThat(messages).hasSize(1);
        assertThat(messages.get(0).getAssignment()).isEqualTo(a);
	}

	private Assignment setupData() {
        Project project = getProject();
        Phase phase = getPhase();
        Task task = getTask();
        phase.getTasks().add(task);
        project.getPhases().add(phase);
        projectStructureService.save(SaveTaskCommand.of(task));
        projectStructureService.save(SavePhaseCommand.of(phase));
        projectStructureService.save(SaveProjectCommand.of(project));

        Assignment a1 = createAssignment(getDate(2019,12,1), getTime(12,30, 0),
                getDate(2019,12,1), getTime(15,30, 0),
                "test", project, phase, task, 1L);
        assignmentService.saveAssignment(SaveAssignmentCommand.of(a1));
        Assignment a2 = createAssignment(getDate(2019,12,1), getTime(12,0, 30),
                getDate(2019,12,1), getTime(15, 0, 0),"test",
                project, phase, task, 2L);
        assignmentService.saveAssignment(SaveAssignmentCommand.of(a2));

        return a2;
    }



    public Project getProject() {
	    return new Project();
    }

    public Phase getPhase() {
	    return new Phase();
    }

    public Task getTask() {
	    return new Task();
    }

}
