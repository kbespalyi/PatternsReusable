package tests;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.HashMap;
import java.util.function.Function;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import utils.Generic;
import utils.ProxySocketMachine;
import utils.DateUtils;

import patterns.*;
import patterns.HelpHandler.Topic;

public class Patterns {

	int numberOfMessagesToAccept = 10;

	// Flyweight Pattern - Structural patterns
	@Test
	public void testFlyweightPattern_thenEquals() {

		DateUtils dateUtils = new DateUtils();

		long numberTasks = 10000000;
		System.out.println("Will be created " + numberTasks);

		System.out.println("Started " + dateUtils.getCurrentDateTime());

		TaskFlyweightCollection tasks = new TaskFlyweightCollection();
		tasks.removeAll();

		long initialMemory = Runtime.getRuntime().totalMemory();

		for (long i = 0; i < numberTasks; i++) {
			// create object and collect to objectsCollector
			tasks.add("task" + i, new TaskProperties() {
				{
					priority = (int) Math.random() * 9;
					project = "Project" + ((int) (Math.random() * 9) + 1);
					user = "User" + ((int) (Math.random() * 9) + 1);
					completed = (int) (Math.random() * 1.5) == 1;
				}
			});
		}

		System.out.println("Finished " + dateUtils.getCurrentDateTime());

		long afterMemory = Runtime.getRuntime().totalMemory();
		System.out.println("Task items " + tasks.getCount());
		System.out.println("Flyweight items " + TaskFlyweightFactory.getCount());
		System.out.println("Total used memory " + (afterMemory - initialMemory) / 10000000);

		assertEquals(numberTasks, tasks.getCount());

		tasks.removeAll();
		tasks = null;
	}

	// Observer Pattern - Behavioral patterns
	@Test
	public void testObserverPattern_thenEquals() {

		ObservableTask task1 = new ObservableTask("Create a demo for constructors", new TaskProperties() {
			{
				user = "Jon";
				project = "Project 1";
				priority = 1;
				completed = false;
			}
		});

		assertEquals("Create a demo for constructors", task1.getName());
		assertEquals("Jon", task1.getUser());
		assertEquals("Project 1", task1.getProject());
		assertEquals(1, task1.getPriority());
		assertEquals(false, task1.isCompleted());

		final Services.IService notifSrv = new Services.NotificationService();
		final Services.IService logSrv = new Services.LoggingService();
		final Services.IService auditSrv = new Services.AuditingService();

		task1.addObserver(notifSrv, new Services.ObserverEvent() {
			@Override
			public void invoke(Task context) {
				notifSrv.update(context);
			}
		});

		task1.addObserver(logSrv, new Services.ObserverEvent() {
			@Override
			public void invoke(Task context) {
				logSrv.update(context);
			}
		});

		task1.addObserver(logSrv, new Services.ObserverEvent() {
			@Override
			public void invoke(Task context) {
				auditSrv.update(context);
			}
		});

		task1.save();
		task1.removeObserver(logSrv.observer);
		task1.save();

	}

	// Mediator Pattern - Behavioral patterns
	@Test
	public void testMediatorPattern_thenEquals() {

		TaskMediator task1 = new TaskMediator("Create a demo for constructors", new TaskProperties() {
			{
				user = "Jon";
				project = "Project 1";
				priority = 1;
				completed = false;
			}
		});

		assertEquals("Create a demo for constructors", task1.getName());
		assertEquals("Jon", task1.getUser());
		assertEquals("Project 1", task1.getProject());
		assertEquals(1, task1.getPriority());
		assertEquals(false, task1.isCompleted());

		final Services.IService notifSrv = new Services.NotificationService();
		final Services.IService logSrv = new Services.LoggingService();
		final Services.IService auditSrv = new Services.AuditingService();

		Mediator.subscribe("complete", notifSrv, new Services.ObserverEvent() {
			@Override
			public void invoke(Task context) {
				notifSrv.update(context);
			}
		});

		Mediator.subscribe("complete", logSrv, new Services.ObserverEvent() {
			@Override
			public void invoke(Task context) {
				logSrv.update(context);
			}
		});

		Mediator.subscribe("complete", auditSrv, new Services.ObserverEvent() {
			@Override
			public void invoke(Task context) {
				auditSrv.update(context);
			}
		});

		task1.complete();

		Mediator.unsubscribe("complete", logSrv);

		task1.complete();

	}

	// Command Pattern - Behavioral patterns
	@Test
	public void testCommandPattern_thenEquals() {

		Repository.execute("save", new Task(1, "Task 1", new TaskProperties() {
			{
				user = "Jon";
				project = "Project 1";
				priority = 1;
				completed = false;
			}
		}));

		Repository.execute("save", new Task(2, "Task 2", new TaskProperties() {
			{
				user = "Jon";
				project = "Project 2";
				priority = 2;
				completed = false;
			}
		}));

		Repository.execute("save", new Task(3, "Task 3", new TaskProperties() {
			{
				user = "Yarn";
				project = "Project 1";
				priority = 1;
				completed = false;
			}
		}));

		Repository.execute("save", new Task(4, "Task 4", new TaskProperties() {
			{
				user = "Bign";
				project = "Project 2";
				priority = 2;
				completed = false;
			}
		}));

		assertEquals(4, Repository.tasks.size());

		// Clean tasks
		Repository.tasks.clear();
		assertEquals(0, Repository.tasks.size());

		// Replay and recovery tasks
		Repository.replay();
		assertEquals(4, Repository.tasks.size());

	}

	// Prototype Pattern - Creational patterns
	@Test
	public void testPrototypePattern_thenEquals() {

		TaskCache.loadCache();

		assertEquals(4, TaskCache.getSize());

		assertEquals("Task 1", TaskCache.getTask(1).getName());
		assertEquals("Task 2", TaskCache.getTask(2).getName());
		assertEquals("Task 3", TaskCache.getTask(3).getName());
		assertEquals("Task 4", TaskCache.getTask(4).getName());

	}

	// Builder Pattern - Creational patterns
	@Test
	public void testBuilderPattern_thenEquals() {

		Task task1 = TaskBuilder.getInstance().buildSprint(new SprintProperties() {
			{
				name = "Sprint 1";
				priority = 1;
				completed = false;
			}
		}).prepareTaskForSprint(1, "Task for Yarn: Project 1", new TaskProperties() {
			{
				user = "Yarn";
				project = "Project 1";
				priority = 1;
				completed = false;
			}
		}).getTask();

		Task task2 = TaskBuilder.getInstance().buildSprint(new SprintProperties() {
			{
				name = "Sprint 2";
				priority = 2;
				completed = false;
			}
		}).prepareTaskForSprint(2, "Task for Born: Project 2", new TaskProperties() {
			{
				user = "Born";
				project = "Project 1";
				priority = 1;
				completed = false;
			}
		}).getTask();

		Task task3 = TaskBuilder.getInstance()
				.prepareTaskForBackLog(3, "Task for Gorn: Project 1", new TaskProperties() {
					{
						user = "Gorn";
						project = "Project 1";
						priority = 1;
						completed = false;
					}
				}).getTask();

		TaskCache.keepToCache(new Task[] { task1, task2, task3 });

		assertEquals(3, TaskCache.getSize());

		assertEquals("Task for Yarn: Project 1", TaskCache.getTask(1).getName());
		assertEquals("Task for Born: Project 2", TaskCache.getTask(2).getName());
		assertEquals("Task for Gorn: Project 1", TaskCache.getTask(3).getName());

		assertEquals("Sprint 1", TaskCache.getTask(1).getSprint().getName());
		assertEquals("Sprint 2", TaskCache.getTask(2).getSprint().getName());
		assertEquals(null, TaskCache.getTask(3).getSprint());

	}

	// Tree of Tasks
	@Test
	public void testTreePattern_thenEquals() {
		// Tree of Topics
		Epic topEpic = new Epic("Top Epic");

		Epic epic1 = new Epic(topEpic, "Epic 1");
		Task task1ToEpic1 = new Task(epic1, "Task 1");
		Epic epic2ToEpic1 = new Epic(epic1, "Epic 2");
		Task task2ToEpic2 = new Task(epic2ToEpic1, "Task 2");
		Task task3ToTask2 = new Task(task2ToEpic2, "Task 3");
		Task task4ToTask2 = new Task(task2ToEpic2, "Task 4");
		Task task5ToEpic2 = new Task(epic2ToEpic1, "Task 5");
		Task task6ToTask5 = new Task(task5ToEpic2, "Task 6");

		Epic epic3 = new Epic(topEpic, "Epic 3");
		Task task7ToEpic3 = new Task(epic3, "Task 7");
		Task task8ToEpic3 = new Task(epic3, "Task 8");
		Task task9ToTask8 = new Task(task8ToEpic3, "Task 9");
		Task task10ToTask8 = new Task(task8ToEpic3, "Task 10");

		assertEquals(Topic.TASK_TOPIC, task4ToTask2.getTopic());
		assertEquals("Task 4", task4ToTask2.getName());

		assertEquals(true, task4ToTask2.hasHelp());
		assertEquals(Topic.TASK_TOPIC, task4ToTask2.getHandler().getTopic());
		assertEquals("Task 2", ((ITopic) task4ToTask2.getHandler()).getName());

		assertEquals(true, task4ToTask2.getHandler().hasHelp());
		assertEquals(Topic.EPIC_TOPIC, task4ToTask2.getHandler().getHandler().getTopic());
		assertEquals("Epic 2", ((ITopic) task4ToTask2.getHandler().getHandler()).getName());

		assertEquals(true, task4ToTask2.getHandler().getHandler().hasHelp());
		assertEquals(Topic.EPIC_TOPIC, task4ToTask2.getHandler().getHandler().getHandler().getTopic());
		assertEquals("Epic 1", ((ITopic) task4ToTask2.getHandler().getHandler().getHandler()).getName());

		assertEquals(true, task4ToTask2.getHandler().getHandler().getHandler().hasHelp());
		assertEquals(Topic.TOP_TOPIC, task4ToTask2.getHandler().getHandler().getHandler().getHandler().getTopic());
		assertEquals("Top Epic", ((ITopic) task4ToTask2.getHandler().getHandler().getHandler().getHandler()).getName());

		assertEquals(Topic.TOP_TOPIC, task1ToEpic1.handleHelp().getTopic());
		assertEquals("Top Epic", ((ITopic) task1ToEpic1.handleHelp()).getName());

		assertEquals(Topic.TOP_TOPIC, task4ToTask2.handleHelp().getTopic());
		assertEquals("Top Epic", ((ITopic) task4ToTask2.handleHelp()).getName());

		assertEquals(Topic.TOP_TOPIC, task3ToTask2.handleHelp().getTopic());
		assertEquals("Top Epic", ((ITopic) task3ToTask2.handleHelp()).getName());

		assertEquals(Topic.TOP_TOPIC, task2ToEpic2.handleHelp().getTopic());
		assertEquals("Top Epic", ((ITopic) task2ToEpic2.handleHelp()).getName());

		assertEquals(Topic.TOP_TOPIC, epic2ToEpic1.handleHelp().getTopic());
		assertEquals("Top Epic", ((ITopic) epic2ToEpic1.handleHelp()).getName());

		assertEquals(Topic.TOP_TOPIC, task10ToTask8.handleHelp().getTopic());
		assertEquals("Top Epic", ((ITopic) task10ToTask8.handleHelp()).getName());

		assertEquals(Topic.TOP_TOPIC, task9ToTask8.handleHelp().getTopic());
		assertEquals("Top Epic", ((ITopic) task9ToTask8.handleHelp()).getName());

		assertEquals(Topic.TOP_TOPIC, task7ToEpic3.handleHelp().getTopic());
		assertEquals("Top Epic", ((ITopic) task7ToEpic3.handleHelp()).getName());

		assertEquals(Topic.TOP_TOPIC, task6ToTask5.handleHelp().getTopic());
		assertEquals("Top Epic", ((ITopic) task6ToTask5.handleHelp()).getName());
	}

	// Chain of Responsibility Pattern - Behavioral patterns
	@Test
	public void testChainOfResponsibilityPattern_thenEquals() {
		Handler rootChain = new Handler();
		rootChain.add(new Handler());
		rootChain.add(new Handler());
		rootChain.add(new Handler());
		rootChain.wrapAround(rootChain);
		for (int i = 1; i < 6; i++) {
			System.out.println("Operation #" + i + ":");
			rootChain.execute(i);
			System.out.println();
		}
	}

	// Memento Pattern - Behavioral patterns
	@Test
	public void testMementoPattern_thenEquals() {
		Caretaker caretaker = new Caretaker();
		Originator originator = new Originator();
		originator.setState("State1");
		originator.setState("State2");
		caretaker.addMemento(originator.save());
		originator.setState("State3");
		caretaker.addMemento(originator.save());
		originator.setState("State4");

		assertEquals(2, caretaker.size());
		assertEquals("State4", originator.getCurrentState());

		originator.restore(caretaker.getMemento());

		assertEquals(2, caretaker.size());
		assertEquals("State3", originator.getCurrentState());
	}

	// PipeLines Pattern - Creational patterns
	@Test
	public void testPipeLinesPattern_thenEquals() {

		Function<String, String> Capitalize = x -> x.substring(0, 1).toUpperCase() + x.substring(1);
		Function<String, String> DoubleSay = x -> x + ", " + x;
		Function<String, String> Exclaim = x -> x + "!";

		String result = Pipeline.apply(Capitalize, DoubleSay, Exclaim).apply("hello");

		assertEquals("Hello, Hello!", result);
	}

	// Adapter Pattern - Structural patterns
	@Test
	public void testAdapterPattern_thenEquals() {
		Issue[] issues = { new TaskAdapter(new Task(1, "Task 1", new TaskProperties() {
			{
				user = "Jon";
				project = "Project 1";
				priority = 1;
				completed = false;
			}
		})), new SprintAdapter(new Sprint(new SprintProperties() {
			{
				name = "Sprint 1";
				priority = 1;
				completed = false;
			}
		})) };

		String json1 = "" + "{\n" + "task: {\n" + "name: \"Task 1\",\n" + "sprint: {},\n" + "project: \"Project 1\",\n"
				+ "priority: 1,\n" + "user: \"Jon\",\n" + "has_completed: false,\n" + "has_saved: false\n" + "},\n"
				+ "helpHandler: {\n" + "topic: \"TOP_TOPIC\",\n" + "has_helper: false\n" + "}\n" + "}";

		String json2 = "" + "{\n" + "name: \"Sprint 1\",\n" + "priority: 1,\n" + "has_completed: false\n" + "}";

		assertEquals(2, issues.length);
		assertEquals(json1, issues[0].getAdapter().toString());
		assertEquals(json2, issues[1].getAdapter().toString());

		for (Issue issue : issues) {
			issue.print();
		}

	}

	// Test Template Method Pattern - Behavioral patterns
	@Test
	public void testTemplateMethodPattern_thenEquals() {
		WorkerStacks.Worker[] workers = { new WorkerStacks.FireFighter(), new WorkerStacks.Lumberjack(),
				new WorkerStacks.Postman(), new WorkerStacks.Manager() };

		for (WorkerStacks.Worker worker : workers) {
			worker.doDayWorkflow();
		}
	}

	// Test Interpreter Pattern - Behavioral patterns
	@Test
	public void testInterpreterPattern_thenEquals() {
		System.out.println("celsius * 9 / 5 + thirty");
		String postfix = InterpreterStacks.convertToPostfix("celsius * 9 / 5 + thirty");
		System.out.println(postfix);
		Operand expr = InterpreterStacks.buildSyntaxTree(postfix);
		expr.traverse(1);
		System.out.println();

		HashMap<String, Integer> map = new HashMap<>();
		map.put("thirty", 30);
		for (int i = 0; i <= 100; i += 10) {
			map.put("celsius", i);
			System.out.println("C is " + i + ",  F is " + expr.evaluate(map));
		}
	}

	// Test Strategy Pattern - Behavioral patterns
	@Test
	public void testStrategyPattern_thenEquals() {
		Strategy[] algorithms = { new StrategyStacks.StrategySolution() {
			private int state = 1;

			protected void start() {
				System.out.print("Start  ");
			}

			protected void stop() {
				System.out.println("Stop");
			}

			protected boolean nextTry() {
				System.out.print("NextTry-" + state++ + "  ");
				return true;
			}

			protected boolean isSolution() {
				System.out.print("IsSolution-" + (state == 3) + "  ");
				return (state == 3);
			}
		}, new StrategyStacks.StrategySearch() {
			private int state = 1;

			protected void preProcess() {
				System.out.print("PreProcess  ");
			}

			protected void postProcess() {
				System.out.print("PostProcess  ");
			}

			protected boolean search() {
				System.out.print("Search-" + state++ + "  ");
				return state == 3 ? true : false;
			}
		} };

		for (Strategy algorithm : algorithms) {
			StrategyStacks.execute(algorithm);
		}
	}

	// Test State Pattern - Behavioral patterns
	@Test
	public void testStatePattern_thenEquals() {
		Chain chain = new Chain();

		assertEquals(Chain.Statuses.OFF, chain.getState());

		chain.pull();
		assertEquals(Chain.Statuses.LOW, chain.getState());

		chain.pull();
		assertEquals(Chain.Statuses.MEDIUM, chain.getState());

		chain.pull();
		assertEquals(Chain.Statuses.HIGH, chain.getState());
	}

	// Test ClassReflect
	@Test
	public void testClassReflection() {
		Type typeOfSrc = Generic.type(Task.class, String.class);
		System.out.println("\nType: " + typeOfSrc);

		Task[] arr = Generic.getArray(Task.class, 10);
		System.out.println("\nArray length: " + arr.length);

		Generic<Task> generic = new Generic<Task>() {
		};

		Class<Task> clazz = generic.getType();
		assertEquals("Task", clazz.getSimpleName());

		System.out.println("\nClass: " + clazz);
	}

	// Test Iterator Pattern - Behavioral patterns
	@Test
	public void testIteratorPattern_thenEquals() {
		VListAbstract.VList<Task> list = new VListAbstract.VList<Task>(0) {
		};

		list.add(new Task(1, "Task 1", new TaskProperties() {
			{
				user = "Jon";
				project = "Project 1";
				sprint = new Sprint("Sprint 1", 9, false);
				priority = 1;
				completed = false;
			}
		}));

		list.add(new Task(2, "Task 2", new TaskProperties() {
			{
				user = "Peter";
				project = "Project 2";
				sprint = new Sprint("Sprint 2", 8, false);
				priority = 2;
				completed = false;
			}
		}));

		assertEquals(2, list.copy().length);

		VListAbstract.VList.printList(list);

		list.remove(0);

		assertEquals(1, list.copy().length);

		VListAbstract.VList.printList(list);
	}

	// Test Actor Pattern - Behavioral patterns
	@Test
	public void testActorCreateAndStart() {
		Actor<String> myActor = Actor.createAndStart(new Actor.Behavior<String>() {
			int counter = 0;

			public boolean onReceive(Actor<String> self, String msg) {
				counter++;
				System.out.println(counter + ". " + msg);
				return (counter < numberOfMessagesToAccept);
			}

			@Override
			public void onException(Actor<String> self, Exception e) {
				System.out.println("Ehhh");
			}
		});

		int count = 0;
		while (true) {
			try {
				myActor.send("toto");
				count++;
			} catch (Actor.DeadException e) {
				assertTrue(count > numberOfMessagesToAccept);
				return;
			}
		}
	}

	// Test LinkedCacheableOptions Pattern - Behavioral patterns
	@Test
	public void testLinkedCacheableOptionsPattern_thenEquals() {

		Options opt1 = new Options(null);
		Options opt2 = new Options(opt1);
		Options opt3 = new Options(opt2);
		opt1.set(opt3);

		assertEquals("NICO", opt1.setAttribute("NAME", "NICO").getAttribute("NAME"));
		assertEquals("LENA", opt1.setAttribute("NAME", "LENA").getAttribute("NAME"));
		assertEquals(5, opt1.setAttribute("TIMES", 5).getAttribute("TIMES"));
		assertEquals(null, opt1.getAttribute("TIMES1"));

		assertEquals("Form1", opt1.setName("Form1").getName());
	}

	// Test Null Object Design Pattern - Behavioral patterns
	@Test
	public void testNullPattern_thenEquals() {
		// 1.
		Application app = new Application(new NullPrintStream());
		app.doSomething();

		// 2.
	}

	// Test Visitor Design Pattern - Behavioral patterns
	@Test
	public void testVisitorPattern_thenEquals() {
		VisitorStacks.Composite[] containers = new VisitorStacks.Composite[3];
		for (int i = 0; i < containers.length; i++) {
			containers[i] = new VisitorStacks.Composite();
			for (int j = 1; j < 4; j++) {
				containers[i].add(new VisitorStacks.Leaf(i * containers.length + j));
			}
		}
		for (int i = 1; i < containers.length; i++) {
			containers[0].add(containers[i]);
		}
		containers[0].traverse();
		System.out.println();
	}

	// Test Object Pool Design Pattern - Creational patterns
	@Test
	public void testObjectPoolPattern_thenEquals() {
		// Create the ConnectionPool:
		ObjectPool.JDBCConnectionPool pool = new ObjectPool.JDBCConnectionPool("org.hsqldb.jdbcDriver",
				"jdbc:hsqldb://localhost/mydb", "sa", "secret");

		// Get a connection:
		Connection con = pool.checkOut();

		// Use the connection

		// Return the connection:
		pool.checkIn(con);
	}

	// Test Proxy Design Pattern - Structural patterns
	@Test
	public void testProxyDesignPattern_thenEquals() {
		Thread thread1 = new Thread(new Runnable() {

			ProxySocketMachine first = new ProxySocketMachine(true);

			public void run() {
				this.first.start();
			}
		});

		Thread thread2 = new Thread(new Runnable() {

			ProxySocketMachine second = new ProxySocketMachine(false);

			public void run() {
				this.second.start();
			}
		});

		thread1.start();
		thread2.start();
	}

	// Test Private Class Data Design Pattern - Structural patterns
	@Test
	public void testPrivateClassDataDesignPattern_thenEquals() {
		final JFrame frame = PrivateClassData.buildFrame();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				PrivateClassData.start(frame);
			}
		});

		while (!PrivateClassData.isExit) {}
		
		System.out.println("Exit");
	}

	@BeforeClass
	public static void setup() {

	}

	@AfterClass
	public static void tearDown() {

	}

}
