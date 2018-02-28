package patterns;

public class Job implements IJob {
	private Task task;
	private Epic epic;
	private Chain position = new Chain();
	
	public Job(TaskProperties taskProp, SprintProperties sprintProp, String epicName) {
		task = TaskBuilder.getInstance().prepareTaskForSprint(taskProp, sprintProp).getTask();
		epic = new Epic(epicName);
	}

	public Job(TaskProperties taskProp, SprintProperties sprintProp) {
		task = TaskBuilder.getInstance().prepareTaskForSprint(taskProp, sprintProp).getTask();
		epic = null;
	}

	public Job(TaskProperties taskProp) {
		task = TaskBuilder.getInstance().prepareTaskForBackLog(taskProp).getTask();
		epic = null;
	}

	public Job(TaskProperties taskProp, String epicName) {
		task = TaskBuilder.getInstance().prepareTaskForBackLog(taskProp).getTask();
		epic = new Epic(epicName);
	}

	public Job(TaskBuilder builder) {
		task = builder.getTask();
		epic = builder.getEpic();
	}

	@Override
	public Task getTask() {
		return task.clone();
	}

	@Override
	public Sprint getSpring() {
		return task.getSprint();
	}

	@Override
	public Epic getEpic() {
		return epic != null ? epic.clone() : null;
	}

	@Override
	public Job up() {
		position.pull();
		position.print();
		return this;
	}

	@Override
	public Chain.Statuses getPosition() {
		return position.getState();
	}

	@Override
	public void start() {
		reset();
	}

	@Override
	public void reset() {
		position = new Chain();
		up();
	}

	@Override
	public boolean hasJob() {
		return true;
	}
	
}
