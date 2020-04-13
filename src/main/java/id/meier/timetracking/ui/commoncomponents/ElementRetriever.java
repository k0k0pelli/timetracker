package id.meier.timetracking.ui.commoncomponents;

import id.meier.timetracking.model.NamedElement;

import java.util.List;

@FunctionalInterface
	public interface ElementRetriever<T extends NamedElement> {
		List<T> getElements();
	}