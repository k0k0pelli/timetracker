package id.meier.timetracking.businesslayer;

import id.meier.timetracking.businesslayer.commands.Command;
import id.meier.timetracking.model.PersistableElement;

public interface Context {
    int getOrder(Command command, PersistableElement... element);
}
