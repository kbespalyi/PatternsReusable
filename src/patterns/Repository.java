package patterns;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

import utils.HashList;

public class Repository {

	public static HashList<Task> tasks = new HashList<Task>();
	public static List<CommandProperties> commands = new ArrayList<CommandProperties>();

	public Repository() {
	}

	public static Task get(int id) {
		System.out.println("Getting task " + id);
		return tasks.findByHash(id);
	}
	
	public static void set(Task task) {
		tasks.addItem(task);
		System.out.println("Persist " + task.getName() + " to the db");
	}
	
	public static void replay() {
		for (int i = 0; i < commands.size(); i++) {
			CommandProperties command = commands.get(i);
			executeNoLog(command.name, (Task) command.obj);
		}
	}
	
	public static void executeNoLog(String methodName, Task task) {

		set(task);
		
		try {
			Method method = Task.class.getMethod(methodName);
			if (method != null) {
		        method.invoke(task);
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
	}

	public static void execute(String methodName, Task task) {

		set(task);

		// Put to history
		commands.add(new CommandProperties(methodName, task));

		try {
			Method method = Task.class.getMethod(methodName);
			if (method != null) {
		        method.invoke(task);
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
	}

}
