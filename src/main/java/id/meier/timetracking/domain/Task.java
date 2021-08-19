package id.meier.timetracking.domain;

import java.util.Objects;

public class Task implements DescribedElement {
	
	private Long id;
	private String name;
	private String description;

	private Boolean active;

	private Task(Long id, String name, String description, Boolean active) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.active = active;

	}

	public Task() {
		active = true;
	}
	
	public Task(String name) {
		this();
		this.name = name;
	}
	
	public Task(String name, String description) {
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

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof Task) {
			Task p = (Task)o;
			Object id1 = (p.getId() != null)?p.getId():p.toString();
			Object id2 = (getId() != null)?getId():this.toString();
			result = Objects.equals(id1, id2);
		}
		return result;
	}

	@Override
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

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private String name;
		private String description;
		private Boolean active;

		private Builder() {
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}


		public Builder withActiveFlag(boolean active) {
			this.active = active;
			return this;
		}

		public Task build() {
			return  new Task(id, name, description, active);
		}

	}
}
