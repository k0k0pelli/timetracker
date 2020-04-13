package id.meier.timetracking.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Project implements DescribedElement {
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;
	@OneToMany(cascade=CascadeType.REMOVE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Phase> phases;


	private Boolean active;

	public Project() {
		phases = new ArrayList<>();
		active = true;
	}
	
	public Project(String name) {
		this();
		this.name = name;
	}
	
	public Project(String name, String description) {
		this(name);
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Phase> getPhases() {
		return phases;
	}
	public void setPhases(List<Phase> phases) {
		this.phases = phases;
	}

	public String toString() {
		return name;
	}
	
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof Project) {
			Project p = (Project)o;
			Object id1 = (p.getId() != null)?p.getId():p.toString();
			Object id2 = (getId() != null)?getId():this.toString();
			result = Objects.equals(id1, id2);
		}
		return result;
	}
	
	public int hashCode() {
		if (id == null) return new Long(0).hashCode();
		return id.hashCode();
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
