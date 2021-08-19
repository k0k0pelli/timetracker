package id.meier.timetracking.application.services.comparator;

import id.meier.timetracking.domain.Assignment;
import org.apache.commons.collections.ComparatorUtils;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component("assignmentProjectPhaseTaskComparator2")
public class AssignmentProjectPhaseTaskComparator implements Comparator<Assignment> {

	@Override
	public int compare(Assignment o1, Assignment o2) {
		Comparator naturalComp = ComparatorUtils.naturalComparator();
		int projectComp = naturalComp.compare(o1.getProjectName(), o2.getProjectName());
		int phaseComp = naturalComp.compare(o1.getPhaseName(), o2.getPhaseName());
		int taskComp = naturalComp.compare(o1.getTaskName(), o2.getTaskName());
		int result = 0;
		if (projectComp == 0) {
			if (phaseComp == 0) {
				if (taskComp > 0) {
					result = 1;
				} else if (taskComp < 0) {
					result = -1;
				}
			} else if (phaseComp > 0) {
				result = 1;
			} else {
				result = -1;
			}
		} else {
			result = projectComp;
		}
		
		return result;
	}

}
