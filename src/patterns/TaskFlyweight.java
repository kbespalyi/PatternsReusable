package patterns;

import patterns.TaskFlyweightFactory.FlyweightTask;

// Flyweight pattern
public class TaskFlyweight {
	
	private String name;
	private boolean saved = false;
	private FlyweightTask flyweight;
	
	TaskFlyweight(String name, TaskProperties properties) {
		this.name = name;
		flyweight = TaskFlyweightFactory.get(properties);
	}

	public void complete() {
		saved = false;
		flyweight.setCompleted(true);
		System.out.println("completing task: " + name);
	}

	public void save() {
		saved = true;
		System.out.println("saving task: " + name);
	}

	public FlyweightTask get() throws CloneNotSupportedException {
		return flyweight.getClone();
	}

	public String getName() {
		return this.name;
	}

	public boolean isSaved() {
		return saved;
	}

	public boolean isCompleted() {
		return flyweight.isCompleted();
	}
}
