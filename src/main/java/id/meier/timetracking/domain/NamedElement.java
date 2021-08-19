package id.meier.timetracking.domain;

public interface NamedElement extends ActivatableElement {
	String getName();
	void setName(String name);
}
