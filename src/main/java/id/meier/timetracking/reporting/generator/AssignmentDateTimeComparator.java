package id.meier.timetracking.reporting.generator;

import id.meier.timetracking.model.Assignment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;


public abstract class AssignmentDateTimeComparator implements Comparator<Assignment> {

	@Override
	public int compare(Assignment o1, Assignment o2) {
		int startDateComp = compare(getDate(o1), getDate(o2));
		int startTimeComp = compare(getTime(o1), getTime(o2));
		
		int result;
		if (startDateComp == 0) {
			result = startTimeComp;
		} else {
			result = startDateComp;
		}
		
		return result;
	}

	private <T extends Comparable<T>> int compare(T o1, T o2) {
		int result = 0;
		if (o1 == null || o2 == null) {
			if (o1 != null) {
				result = 1;
			} else if (o2 != null) {
				result = -1;
			}
		} else {
			result = o1.compareTo(o2);
		}
		return result;
	}

	protected abstract LocalTime getTime(Assignment a);

	protected abstract LocalDate getDate(Assignment a);

}
