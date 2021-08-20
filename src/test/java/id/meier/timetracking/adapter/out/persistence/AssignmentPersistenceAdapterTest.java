package id.meier.timetracking.adapter.out.persistence;




import id.meier.timetracking.application.port.out.commands.SaveAssignmentEntityCommand;
import id.meier.timetracking.application.port.out.commands.SelectAssignmentEntityCommand;
import id.meier.timetracking.domain.Assignment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AssignmentPersistenceAdapterTest {

    @Autowired
    private AssignmentPersistenceAdapter assignmentPersistenceAdapter;

    @Test
    public void testAssignmentStoring() {
        Assignment assignment = Assignment.builder().build();
        assignment = assignmentPersistenceAdapter.saveAssignment(SaveAssignmentEntityCommand.of(assignment));
        assertTrue(assignment != null);

        List<Assignment> assignment1 = assignmentPersistenceAdapter.selectAssignments(SelectAssignmentEntityCommand.builder().build());
        Assertions.assertThat(assignment1).hasSize(1);
    }

}