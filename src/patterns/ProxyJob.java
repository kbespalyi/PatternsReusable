package patterns;

import patterns.Chain.Statuses;

public class ProxyJob implements IJob {

	private TaskBuilder builder;
	private Job job;
	
	public ProxyJob(TaskProperties taskProp, SprintProperties sprintProp, String epicName) {
		builder = TaskBuilder.getInstance().prepareTaskForSprint(taskProp, sprintProp).setEpic(new Epic(epicName));
	}
	
	@Override
	public IJob up() {
		if (job == null) {
			job = new Job(builder);
		}
		return job.up();
	}

	@Override
	public void start() {
		if (job == null) {
			job = new Job(builder);
		}
		job.start();
	}
	
	@Override
	public void reset() {
		if (job == null) {
			job = new Job(builder);
		} else {
			job.reset();
		}
	}

	@Override
	public Task getTask() {
		if (job == null) {
			job = new Job(builder);
		}
		return job.getTask();
	}

	@Override
	public Sprint getSpring() {
		if (job == null) {
			job = new Job(builder);
		}
		return job.getSpring();
	}

	@Override
	public Epic getEpic() {
		if (job == null) {
			job = new Job(builder);
		}
		return job.getEpic();
	}

	@Override
	public Statuses getPosition() {
		if (job == null) {
			job = new Job(builder);
		}
		return job.getPosition();
	}
	
	@Override
	public boolean hasJob() {
		return job != null ? true : false;
	}

}
