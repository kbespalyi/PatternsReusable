package patterns;

import utils.HashList;

public class TaskCache {

	private static HashList<Task> cachedTasks = new HashList<Task>(); 
	
	public TaskCache() {}
	
	public static Task getTask(int Id) {
		Task task = null;
		Task item = cachedTasks.findByHash(Id);
		if (item != null) {
			task = item.clone();
		}
		return task;
	}
	
	public static int getSize() {
		return cachedTasks.size();
	}
	
	private static Task setTask(Task task) {
		return cachedTasks.addItem(task);
	}
	
	public static void clearCache() {
		cachedTasks.clear();
	}
	
	public static void loadCache() {

		setTask(new Task(1, "Task 1",
				new TaskProperties() {
				{
					user = "Jon";
					project = "Project 1";
					priority = 1;
					completed = false;
				}
		}));
		
		setTask(new Task(2, "Task 2",
				new TaskProperties() {
				{
					user = "Jon";
					project = "Project 2";
					priority = 2;
					completed = false;
				}
		}));
		
		setTask(new Task(3, "Task 3",
				new TaskProperties() {
				{
					user = "Yarn";
					project = "Project 1";
					priority = 1;
					completed = false;
				}
		}));
		
		setTask(new Task(4, "Task 4",
				new TaskProperties() {
				{
					user = "Bign";
					project = "Project 2";
					priority = 2;
					completed = false;
				}
		}));
		
	}
	
	public static void keepToCache(Task ...tasks) {
		for (Task task: tasks) {
			cachedTasks.addItem(task.clone());
		}
	}
}
