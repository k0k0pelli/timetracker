package id.meier.timetracking.application.port.in.assignmentmangement;





import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IConsistencyMessage;
import id.meier.timetracking.domain.Assignment;

import java.util.List;

public interface ConsistencyCheckUseCase {
     List<IConsistencyMessage> checkConsistency(Assignment assignment);
}
