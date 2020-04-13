package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.dblayer.repository.RepositoryAccessor;

public abstract class BaseCommand implements Command {
    RepositoryAccessor repoAccessor;
    int order = 0;

    @Override
    public void setRepoAccessor(RepositoryAccessor repoAccessor) {
        this.repoAccessor = repoAccessor;
    }

    @Override
    public RepositoryAccessor getRepoAccessor() {
        return this.repoAccessor;
    }

    @Override
    public abstract void computeOrder(Context context);

    @Override
    public int getOrder() {
        return this.order;
    }
}
