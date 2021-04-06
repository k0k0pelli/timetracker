package id.meier.timetracking.businesslayer;

import id.meier.timetracking.businesslayer.commands.Command;
import id.meier.timetracking.db.dto.PersistableElement;

public interface Context {
    int getOrder(Command command, PersistableElement... element);
}
