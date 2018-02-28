package patterns;

public class TaskBuilder {
	
	private Sprint sprint;
	private Task task;
	private Epic epic;

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

	public TaskBuilder prepareTaskForSprint(TaskProperties taskProp, SprintProperties sprintProp) {
		task = new Task(taskProp);
		buildSprint(sprintProp);
		task.setSprint(sprint);
		return this;
	}

	public TaskBuilder prepareTaskForBackLog(int id, String name, TaskProperties properties) {
		task = new Task(id, name, properties);
		return this;
	}

	public TaskBuilder prepareTaskForBackLog(TaskProperties properties) {
		task = new Task(properties);
		return this;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public TaskBuilder setSprint(Sprint sprint) {
		this.sprint = sprint;
		return this;
	}
	
	public TaskBuilder setEpic(Epic epic) {
		this.epic = epic;
		return this;
	}

	public Task getTask() {
		return task.clone();
	}

	public Epic getEpic() {
		return epic.clone();
	}
}
