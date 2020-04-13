package id.meier.timetracking.businesslayer.commands;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.model.PersistableElement;

public class SaveCommand<T extends PersistableElement> extends BaseCommand {

	private final T element;

	public SaveCommand(T element) {	
		this.element = element;
	}
	public  void execute() {
		repoAccessor.save(element);
	}

	@Override
	public void computeOrder(Context context) {
		this.order =  context.getOrder(this, element);
	}
}
