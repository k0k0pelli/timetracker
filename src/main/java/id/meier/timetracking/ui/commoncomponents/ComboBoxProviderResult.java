package id.meier.timetracking.ui.commoncomponents;

import id.meier.timetracking.model.NamedElement;

import java.util.List;

public class ComboBoxProviderResult<T extends NamedElement> {
	private final T addedElement;
	private final List<T> allElements;
	
	ComboBoxProviderResult(T addedElement, List<T> allElements) {
		this.addedElement = addedElement;
		this.allElements = allElements;
	}
	public T getAddedElement() {
		return addedElement;
	}
	public List<T> getAllElements() {
		return allElements;
	}
}
