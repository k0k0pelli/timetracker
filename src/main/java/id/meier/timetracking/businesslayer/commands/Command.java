package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.db.repository.RepositoryAccessor;

public interface Command {
	void execute();
	RepositoryAccessor getRepoAccessor();
	void setRepoAccessor(RepositoryAccessor repoAccessor);

	int getOrder();
	void computeOrder(Context context);
}