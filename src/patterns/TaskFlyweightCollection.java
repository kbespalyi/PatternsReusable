package patterns;

import java.util.HashMap;

import patterns.TaskProperties;

public class TaskFlyweightCollection {

	private HashMap<String, TaskFlyweight> tasks = new HashMap<String, TaskFlyweight>();

	public int add(String name, TaskProperties properties) {
		tasks.put(name, new TaskFlyweight(name, properties));
		return getCount();
	}
	
	public TaskFlyweight get(String name) {
		return tasks.get(name);
	}

	public TaskFlyweight remove(String name) {
		return tasks.remove(name);
	}

	public void removeAll() {
		tasks.clear();
		TaskFlyweightFactory.clear();
	}

	public int getCount() {
		return tasks.size();
	}
	
}
