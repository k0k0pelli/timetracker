package id.meier.timetracking.db.dto;

public interface DescribedElement extends NamedElement {
	String getDescription();
	void setDescription(String description);

}
