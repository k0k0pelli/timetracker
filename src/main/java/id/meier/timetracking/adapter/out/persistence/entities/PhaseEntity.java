package id.meier.timetracking.adapter.out.persistence.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name="Phase2")
public class PhaseEntity {
	@Id
	@GeneratedValue
	private Long id; 
	private String name;
	private String description;
	private Boolean active;

	@OneToMany(cascade= CascadeType.REMOVE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TaskEntity> tasks;

	public PhaseEntity() {
		active = true;
		tasks = new ArrayList<>();
	}
	
	public PhaseEntity(String name, String description) {
		this();
		this.name = name;
		this.description = description;
	}
	
	public PhaseEntity(String name) {
		this.name = name;
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
	
	public String toString() {
		return name;
	}
	
	
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof PhaseEntity) {
			PhaseEntity p = (PhaseEntity)o;
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


	public List<TaskEntity> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskEntity> tasks) {
		this.tasks = tasks;
	}
}
