package patterns;

public class TaskMediator extends Task {

	public TaskMediator(String name, TaskProperties properties) {
		super(name, properties);
	}

	@Override
	public void complete() {
		Mediator.publish("complete", this);
		super.complete();
	}

}
