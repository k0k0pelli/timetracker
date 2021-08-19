package id.meier.timetracking.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Project implements DescribedElement {
	private Long id;
	private String name;
	private String description;
	private List<Phase> phases;
	private Boolean active;

	private Project(Long id, String name, String description, List<Phase> phases, Boolean active) {
		this.phases = new ArrayList<>();
		this.id = id;
		this.name = name;
		this.description = description;
		this.phases.addAll(phases);
		this.active = active;
	}

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

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private String name;
		private String description;
		private List<Phase> phases;
		private Boolean active;

		private Builder() {
			phases = new ArrayList<>();
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

		public Builder withPhase(Phase phase) {
			this.phases.add(phase);
			return this;
		}

		public Builder withPhases(Phase... phases) {
			this.phases.addAll(Arrays.asList(phases));
			return this;
		}

		public Builder withActiveFlag(boolean active) {
			this.active = active;
			return this;
		}

		public Project build() {
			return  new Project(id, name, description, phases, active);
		}

	}
}
