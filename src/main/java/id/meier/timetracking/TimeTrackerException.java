package id.meier.timetracking;

public class TimeTrackerException extends RuntimeException {
	public TimeTrackerException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public TimeTrackerException(String msg) {
		super(msg);
	}
	
	public TimeTrackerException() {
	}
}
