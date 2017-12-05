package patterns;

public class Sprint implements Cloneable {

	private String name;
	private int priority;
	private boolean completed;

	public Sprint(String name, int priority, boolean completed) {
		this.name = name;
		this.priority = priority;
		this.completed = completed;
	}

	public Sprint(SprintProperties properies) {
		this.name = properies.name;
		this.priority = properies.priority;
		this.completed = properies.completed;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isCompleted() {
		return this.completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.completed ? 1231 : 1237);
		result = prime * result + this.priority;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Sprint)) {
			return false;
		}
		Sprint other = (Sprint) obj;
		if (this.completed != other.completed) {
			return false;
		}
		if (this.priority != other.priority) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
	public Sprint getClone() throws CloneNotSupportedException {
		return (Sprint) super.clone();
	}

	@Override
	public Sprint clone() {
		Sprint clone = null;
		try {
			clone = this.getClone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}

	@Override
	public String toString() {
		return "{\nname: \"" + getName() + "\",\npriority: " + getPriority() + ",\nhas_completed: " + isCompleted() + "\n}";
	}
	
}
