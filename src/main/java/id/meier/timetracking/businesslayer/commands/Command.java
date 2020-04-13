package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.dblayer.repository.RepositoryAccessor;

public interface Command {
	void execute();
	RepositoryAccessor getRepoAccessor();
	void setRepoAccessor(RepositoryAccessor repoAccessor);

	int getOrder();
	void computeOrder(Context context);
}