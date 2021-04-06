package id.meier.timetracking.ui.projects;

import id.meier.timetracking.db.dto.DescribedElement;

@FunctionalInterface
interface ElementViewChangeListener<V extends DescribedElement> {
	void viewElementSelected(V element);
 }