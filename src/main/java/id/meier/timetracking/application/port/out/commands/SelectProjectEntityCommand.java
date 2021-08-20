package id.meier.timetracking.application.port.out.commands;

import id.meier.timetracking.domain.Project;

import java.util.Optional;

public class SelectProjectEntityCommand {
	private  Long id;

	private SelectProjectEntityCommand() { }
	private SelectProjectEntityCommand(Long id) {
		this.id = id;
	}

	public Optional<Long> getId() { return Optional.ofNullable(id); }


	public static SelectProjectEntityCommand of(Long id) {
		return new SelectProjectEntityCommand(id);
	}

	public static SelectProjectEntityCommand all() {
		return new SelectProjectEntityCommand();
	}
}
