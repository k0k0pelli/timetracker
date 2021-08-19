package id.meier.timetracking.web.commoncomponents;

import id.meier.timetracking.domain.NamedElement;

import java.util.List;

@FunctionalInterface
	public interface ElementRetriever<T extends NamedElement> {
		List<T> getElements();
	}