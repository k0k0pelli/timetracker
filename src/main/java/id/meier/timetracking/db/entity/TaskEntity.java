package id.meier.timetracking.db.entity;

import id.meier.timetracking.db.dto.DescribedElement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class TaskEntity implements DescribedElement {
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;

	private Boolean active;

	public TaskEntity() {
		active = true;
	}
	
	public TaskEntity(String name) {
		this();
		this.name = name;
	}
	
	public TaskEntity(String name, String description) {
		this();
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
	
	public String toString() {
		return name;
	}

	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof TaskEntity) {
			TaskEntity p = (TaskEntity)o;
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
