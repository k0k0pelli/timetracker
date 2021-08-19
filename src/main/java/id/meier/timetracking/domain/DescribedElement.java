package id.meier.timetracking.domain;

public interface DescribedElement extends NamedElement {
	String getDescription();
	void setDescription(String description);

}
