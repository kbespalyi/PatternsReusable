package patterns;

public class TaskBuilder {
	
	private Sprint sprint;
	private Task task;

	public TaskBuilder() {}
	
	public static TaskBuilder getInstance() {
		return new TaskBuilder();
	}

	public TaskBuilder buildSprint(String name, int priority, boolean completed) {
		sprint = new Sprint(name, priority, completed);
		return this;
	}

	public TaskBuilder buildSprint(SprintProperties properties) {
		sprint = new Sprint(properties);
		return this;
	}
	
	public TaskBuilder prepareTaskForSprint(int id, String name, TaskProperties properties) {
		task = new Task(id, name, properties);
		task.setSprint(sprint);
		return this;
	}
	
	public TaskBuilder prepareTaskForBackLog(int id, String name, TaskProperties properties) {
		task = new Task(id, name, properties);
		return this;
	}
	
	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public Task getTask() {
		return task;
	}
}
