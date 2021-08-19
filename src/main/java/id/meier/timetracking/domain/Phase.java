package id.meier.timetracking.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Phase  implements DescribedElement {
	private Long id;
	private String name;
	private String description;
	private Boolean active;

	private List<Task> tasks;

	private Phase(Long id, String name, String description, List<Task> tasks,  Boolean active) {
		this.tasks = new ArrayList<>();
		this.id = id;
		this.name = name;
		this.description = description;
		this.tasks.addAll(tasks);
		this.active = active;
	}

	public Phase() {
		active = true;
		tasks = new ArrayList<>();
	}
	
	public Phase(String name, String description) {
		this();
		this.name = name;
		this.description = description;
		tasks = new ArrayList<>();
	}
	
	public Phase(String name) {
		this.name = name;
		tasks = new ArrayList<>();
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

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return name;
	}
	
	
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof Phase) {
			Phase p = (Phase)o;
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


	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private String name;
		private String description;
		private List<Task> tasks;
		private Boolean active;

		private Builder() {
			tasks = new ArrayList<>();
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

		public Builder withTask(Task task) {
			this.tasks.add(task);
			return this;
		}

		public Builder withTasks(Task... tasks) {
			this.tasks.addAll(Arrays.asList(tasks));
			return this;
		}

		public Builder withActiveFlag(boolean active) {
			this.active = active;
			return this;
		}

		public Phase build() {
			return  new Phase(id, name, description, tasks, active);
		}

	}
}
