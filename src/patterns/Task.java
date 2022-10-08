package patterns;

import java.util.HashMap;

public class Task extends HelpHandler implements ITopic, Cloneable {
	private int id = 0;
	private String name = "";
	private String project = "";
	private String user = "";
	private int priority = 0;
	private Sprint sprint = null;
	private boolean completed = false;
	private boolean saved = false;

	private HashMap<String, Integer> invokes = new HashMap<String, Integer>();
	
	public Task(String name, TaskProperties properties) {
		this.name = name;
		this.user = properties.user;
		this.project = properties.project;
		this.priority = properties.priority;
		this.completed = properties.completed;
	}

	public Task(TaskProperties properties) {
		this.name = properties.project.concat(": " + properties.user).concat(" - " + properties.priority);
		this.user = properties.user;
		this.project = properties.project;
		this.priority = properties.priority;
		this.completed = properties.completed;
	}

	public Task(int id, String name, TaskProperties properties) {
		this.id = id;
		this.name = name;
		this.user = properties.user;
		this.project = properties.project;
		this.priority = properties.priority;
		this.completed = properties.completed;
		this.sprint = properties.sprint;
	}

	public Task(String project, int priority, String user, boolean completed) {
		this.name = project.concat(": " + user).concat(" - " + priority);
		this.user = user;
		this.project = project;
		this.priority = priority;
		this.completed = completed;
	}

	public Task(HelpHandler s, String name) {
		super(s, Topic.TASK_TOPIC);
		this.name = name;
	}

	public Task(String name) {
		super(null, Topic.TOP_TOPIC);
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Task setName(String name) {
		this.name = name;
		return this;
	}

	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Sprint getSprint() {
		return sprint != null ? sprint.clone () : null;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public boolean isCompleted() {
		return this.completed;
	}

	public boolean isSaved() {
		return this.saved;
	}

	public void complete() {
		this.completed = true;
		System.out.println("completing task: " + name);
	}

	public void save() {
		this.saved = true;
		System.out.println("saving task: " + name);
	}
	
	public void update(String message) {
		System.out.println(message);
		if (invokes.containsKey(message)) {
			invokes.put(message, invokes.get(message).intValue() + 1);
		} else {
			invokes.put(message, 1);
		}
	}

	public int getLogByMessage(String message) {
		int result = 0;
		if (invokes.containsKey(message)) {
			result = invokes.get(message).intValue();
		}
		return result;
	}

	@Override
	public int hashCode() {
		int result = 1;
		if (this.id > 0) {
			result = this.id;
		} else {
			final int prime = 31;
			result = prime * result + (this.completed ? 1231 : 1237);
			result = prime * result + this.priority;
			result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
			result = prime * result + ((this.project == null) ? 0 : this.project.hashCode());
			result = prime * result + ((this.user == null) ? 0 : this.user.hashCode());
			result = prime * result + ((this.sprint == null) ? 0 : this.sprint.hashCode());
		}
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
		if (!(obj instanceof Task)) {
			return false;
		}

		Task other = (Task) obj;

		if (this.hashCode() == other.hashCode()) {
			return true;
		}
		
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
		if (this.project == null) {
			if (other.project != null) {
				return false;
			}
		} else if (!this.project.equals(other.project)) {
			return false;
		}
		if (this.user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!this.user.equals(other.user)) {
			return false;
		}
		return true;
	}
	
	public Task getClone() throws CloneNotSupportedException {
		return (Task) super.clone();
	}

	@Override
	public Task clone() {
		Task clone = null;
		try {
			clone = this.getClone();
		} catch (CloneNotSupportedException e) {
		}
		return clone;
	}
	
	@Override
	public String toString() {
		return "{\ntask: {\nname: \"" + getName() + "\",\nsprint: " + (getSprint() != null ? getSprint().toString() : "{}") + ",\nproject: \"" + getProject() + "\",\npriority: " + getPriority()
				+ ",\nuser: \"" + getUser() + "\",\nhas_completed: " + isCompleted() + ",\nhas_saved: " + isSaved() + "\n},\n" + super.toString() +"\n}";
	}

	@Override
	public ITopic setAttribute(String attribute, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(String attribute) {
		// TODO Auto-generated method stub
		return null;
	}

}
