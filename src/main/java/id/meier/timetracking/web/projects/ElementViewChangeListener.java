package id.meier.timetracking.web.projects;

import id.meier.timetracking.domain.DescribedElement;

@FunctionalInterface
interface ElementViewChangeListener<V extends DescribedElement> {
	void viewElementSelected(V element);
 }