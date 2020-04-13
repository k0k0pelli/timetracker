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
import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.businesslayer.consistency.ConsistencyProblem;
import id.meier.timetracking.businesslayer.consistency.IConsistencyChecker;
import id.meier.timetracking.businesslayer.consistency.IConsistencyMessage;
import id.meier.timetracking.businesslayer.consistency.impl.ConsistencyChecker;
import id.meier.timetracking.businesslayer.consistency.impl.rules.DescriptionCheckRule;
import id.meier.timetracking.businesslayer.consistency.impl.rules.EndDateTimeCheckRule;
import id.meier.timetracking.businesslayer.consistency.impl.rules.ProjectPhaseTaskCheckRule;
import id.meier.timetracking.businesslayer.consistency.impl.rules.StartDateTimeCheckRule;
import id.meier.timetracking.dblayer.repository.RepositoryAccessor;
import id.meier.timetracking.model.Assignment;
import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import id.meier.timetracking.model.Task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
		locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ConsistencyTest extends TestBase {



	@Autowired
	private RepositoryAccessor repositoryAccessor;

	@Autowired
	private IConsistencyChecker checker;


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
        repositoryAccessor.save(task);
        repositoryAccessor.save(phase);
        repositoryAccessor.save(project);

        Assignment a1 = createAssignment(getDate(2019,12,1), getTime(12,30,00),
                getDate(2019,12,1), getTime(15,30,00),
                "test", project, phase, task, 1l);
        repositoryAccessor.save(a1);
        Assignment a2 = createAssignment(getDate(2019,12,1), getTime(12,30,00),
                getDate(2019,12,1), getTime(15,00,00),"test",
                project, phase, task, 2l);
        repositoryAccessor.save(a2);

        return a2;
    }

	private List<Assignment> createAssignmentsTest1() {
		List<Assignment> assignments = new ArrayList<>();
		//assignments.add(createAssignment(dayOne(), timeOne(), dayOne(), timeTwo(), project(), phase(), task(), "description" ));
		//assignments.add(createAssignment(dayOne(), timeTwo(), dayOne(), timeThree(), project(), phase(), task(), "description" ));
		return assignments;
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
