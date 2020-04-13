package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.model.PersistableElement;

public class DeleteCommand<T extends PersistableElement> extends BaseCommand {

	private final T element;

	public DeleteCommand(T element) {		
		this.element = element;
	}

	public void execute() {
		repoAccessor.delete(element);
	}

	@Override
	public void computeOrder(Context context) {
		this.order =  context.getOrder(this, element);
	}

}
