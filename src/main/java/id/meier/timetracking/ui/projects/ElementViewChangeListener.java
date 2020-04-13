package id.meier.timetracking.ui.projects;

import id.meier.timetracking.model.DescribedElement;

@FunctionalInterface
interface ElementViewChangeListener<V extends DescribedElement> {
	void viewElementSelected(V element);
 }