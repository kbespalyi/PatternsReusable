package patterns;

public class TaskMediator extends Task {

	public TaskMediator(String name, TaskProperties properties) {
		super(name, properties);
	}

	@Override
	public void complete() {
		super.complete();
		Mediator.publish("complete", this);
	}

	@Override
	public void save() {
		super.save();
		Mediator.publish("save", this);
	}

}
