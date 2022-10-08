package tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import utils.Generic;
import utils.ProxySocketMachine;
import utils.DateUtils;

import patterns.*;
import patterns.HelpHandler.Topic;

/*@RunWith(MockitoJUnitRunner.class)*/
@SuppressWarnings("unused")
public final class Patterns {

	int numberOfMessagesToAccept = 10;
	static ServerSocket mockServerSocket;
	static Socket mockTestClientSocket;
	
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
		assertEquals(false, task1.isSaved());

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

		Mediator.subscribe("save", logSrv, new Services.ObserverEvent() {
			@Override
			public void invoke(Task context) {
				logSrv.update(context);
			}
		});

		Mediator.subscribe("save", auditSrv, new Services.ObserverEvent() {
			@Override
			public void invoke(Task context) {
				auditSrv.update(context);
			}
		});

		task1.complete();
		task1.save();

		Mediator.unsubscribe("complete", logSrv);
		Mediator.unsubscribe("save", logSrv);

		task1.complete();
		task1.save();

		assertEquals("Create a demo for constructors", task1.getName());
		assertEquals("Jon", task1.getUser());
		assertEquals("Project 1", task1.getProject());
		assertEquals(1, task1.getPriority());
		assertEquals(true, task1.isCompleted());
		assertEquals(true, task1.isSaved());
		assertEquals(2, task1.getLogByMessage(notifSrv.getMessage()));
		assertEquals(3, task1.getLogByMessage(logSrv.getMessage()));
		assertEquals(2, task1.getLogByMessage(auditSrv.getMessage()));
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
		Strategy[] algorithms = {
			new StrategyStacks.StrategySolution() {
				private int state = 1;
	
				protected void start() {
					System.out.print("Start  ");
				}
	
				protected void stop() {
					System.out.println("Stop");
				}
	
				protected boolean nextTry() {
					System.out.print("NextTry-" + (state++) + "  ");
					return true;
				}
	
				protected boolean isSolution() {
					System.out.print("IsSolution-" + (state == 3) + "  ");
					return (state == 3);
				}
			},
			new StrategyStacks.StrategySearch() {
				private int state = 1;
	
				protected void preProcess() {
					System.out.print("PreProcess  ");
				}
	
				protected void postProcess() {
					System.out.print("PostProcess  ");
				}
	
				protected boolean search() {
					System.out.print("Search-" + (state++) + "  ");
					return state == 3 ? true : false;
				}
			}
		};

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

	// Test Facade Design Pattern - Structural patterns
	@Test
	public void testFacadePattern_thenEquals() {
		Job job1 = new Job(new TaskProperties() {
			{
				user = "Jon";
				project = "Project 1";
				priority = 1;
				completed = false;
			}
		}, new SprintProperties() {
			{
				name = "Sprint 1";
				priority = 1;
				completed = false;
			}
		}, "Epic1");

		assertEquals(job1.hasJob(), true);
		
		assertEquals(job1.getTask().getName(), "Project 1: Jon - 1");
		assertEquals(job1.getEpic().getName(), "Epic1");
		assertEquals(job1.getPosition().toString(), "OFF");

		assertEquals(job1.hasJob(), true);
		
		job1.start();		
		assertEquals(job1.getPosition().toString(), "LOW");

		job1.up();		
		assertEquals(job1.getPosition().toString(), "MEDIUM");

		job1.up();		
		assertEquals(job1.getPosition().toString(), "HIGH");

		job1.up();		
		assertEquals(job1.getPosition().toString(), "OFF");

		job1.up();		
		assertEquals(job1.getPosition().toString(), "LOW");
	}

	/** Test Proxy Design Pattern - Structural patterns
	 * Proxy Design Pattern comes into picture here as it defers the Object Creation process of memory-intensive components thereby speeding up the Application.
	 * 
	 * There are different types of proxy patterns. Virtual Proxy is one of them. Others (from GOF) are Protection Proxy, Remote Proxy, Smart Reference.
	 * From GOF:
	 * A remote proxy provides a local representative for an object in a different address space.
	 * 	- Virtual Proxy is an object with the same interface as the real object.
	 * 	- The first time one of its methods are called it loads the real object and then delegates.
	 * A virtual proxy creates expensive objects on demand.
	 * A protection proxy controls access to the original object. Protection proxies are useful when objects should have different access rights.
	 * A smart reference is a replacement for a bare pointer that performs additional actions when an object is accessed
	 */
	@Test
	public void testProxyDesignPattern_thenEquals() {
		IJob job1 = new ProxyJob(new TaskProperties() {
			{
				user = "Jon";
				project = "Project 1";
				priority = 1;
				completed = false;
			}
		}, new SprintProperties() {
			{
				name = "Sprint 1";
				priority = 1;
				completed = false;
			}
		}, "Epic1");
		
		assertEquals(job1.hasJob(), false);
		
		assertEquals(job1.getTask().getName(), "Project 1: Jon - 1");
		assertEquals(job1.getEpic().getName(), "Epic1");
		assertEquals(job1.getPosition().toString(), "OFF");

		assertEquals(job1.hasJob(), true);
		
		job1.start();		
		assertEquals(job1.getPosition().toString(), "LOW");

		job1.up();		
		assertEquals(job1.getPosition().toString(), "MEDIUM");

		job1.up();		
		assertEquals(job1.getPosition().toString(), "HIGH");

		job1.up();		
		assertEquals(job1.getPosition().toString(), "OFF");

		job1.up();		
		assertEquals(job1.getPosition().toString(), "LOW");
		
	}

	/**
	 * Gateway/Mapper/Registry Pattern Design
	 */
	@Test
	public void testGatewayMapperRegistryDesignPatterns() {
		
	}
	
	// Test Object Pool Design Pattern - Creational patterns
	@Test
	@Disabled("for demonstration purposes")
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
	@Disabled("for demonstration purposes")
	public void testProxyDesignPattern_mockServerSocket() {
		// Server
		Thread thread1 = new Thread(new Runnable() {

			ProxySocketMachine first = new ProxySocketMachine(mockServerSocket);

			public void run() {
				this.first.start();
			}
		});

		thread1.start();
	}

	// Test Private Class Data Design Pattern - Structural patterns
	@Test
	@Disabled("for demonstration purposes")
	@DisplayName("testPrivateClassDataDesignPattern_thenEquals - 😱")
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

	// Test Methods
	@Test
	public void testClassMethods() {
		// 1.
		class TestMethods {

		    /** Test Binary Search
		     * @param nums ordered sequence of integers
		     * @param key  an element for searching
		     * @return index of key or a negative value
		     */
		    public int callBinarySearch(int[] nums, int key) {
		        int len = nums.length;
		        int idx = -1;
		        while (++idx < len) {
		            if (nums[idx] == key) {
		                break;
		            }
		        }
		        if (idx >= len) {
		        	idx = -1;
		        }
		        return idx;
		    }

		    /** Test Binary Revert
		     * @param nums ordered sequence of integers
		     * @return reversed array
		     */
		    public int[] callRevereArray(int[] nums) {
		        int len = nums.length;
		        int half = len / 2;
		        int idx = -1;
		        while (++idx < half) {
		            int c = nums[idx];
		            nums[idx] = nums[len - idx - 1];
		            nums[len - idx - 1] = c;
		        }
		        return nums;
		    }

		    /** Find the smallest positive integer
		     * Given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.
			 * For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.
	     	 * Given A = [1, 2, 3], the function should return 4.
		     * Given A = [−1, −3], the function should return 1.
		     * Write an efficient algorithm for the following assumptions:
		     * N is an integer within the range [1..100,000];
		     * each element of array A is an integer within the range [−1,000,000..1,000,000].
		     */
		    public int callSmallPositiveInt(int[] A) {
		        int test = 1;
		        int len = A.length;
		        Arrays.sort(A);
		        for ( int idx = 0; idx < len; idx++ ) {
		            if (A[idx] >= test) {
		            	if (A[idx] == test) {
			                test++;
			                continue;
			            } else {
			                break;
			            }
		            } 
		        }
		        return test;
		    }
		    
		    /** BinaryGap
		     * A binary gap within a positive integer N is any maximal sequence of consecutive zeros that is surrounded
		     * by ones at both ends in the binary representation of N. For example, number 9 has binary representation 1001
		     * and contains a binary gap of length 2. The number 529 has binary representation 1000010001
		     * and contains two binary gaps: one of length 4 and one of length 3. The number 20 has binary
		     * representation 10100 and contains one binary gap of length 1. The number 15 has binary representation 1111
		     * and has no binary gaps. The number 32 has binary representation 100000 and has no binary gaps.
		     * that, given a positive integer N, returns the length of its longest binary gap.
		     * The function should return 0 if N doesn't contain a binary gap. For example, given N = 1041
		     * the function should return 5, because N has binary representation 10000010001 and so its longest
		     * binary gap is of length 5. Given N = 32 the function should return 0, because N has binary representation '100000'
		     * and thus no binary gaps.
		     * Write an efficient algorithm for the following assumptions: N is an integer within the range [1..2,147,483,647].
		     */
		    public int callBinaryGapLength(int N) {
		    	int result = 0;
		    	
		    	if (N < 1 || N > 2147483647) {
		    		return result;
		    	}
		    	
		    	int currentGap = 0;
		    	int currentGapLen = 0;
		    	char prev = ' ';

		    	String binary = Integer.toBinaryString(N);
		    	int index = binary.length();
		    	while (index-- > 0) {
		    		char ch = binary.charAt(index);
		    		if (ch == '0') {
		    			if (prev == '1') {
		    				currentGap++;
		    			}
		    			currentGapLen++;
		    		} else {
		    			if (currentGap > 0) {
		    				result = Math.max(currentGapLen, result);
		    			}
	    				currentGapLen = 0;
		    		}
		    		prev = ch;
		    	}

		    	return currentGap > 0 ? Math.max(currentGapLen, result) : 0;
		    }
		    
		    private int getMin(int arr[], int n) {
	    	    int min = arr[0];
	    	    for (int i = 1; i < n; i++)
	    	        if (arr[i] < min) min = arr[i];
	    	    return min;
	    	}
		    
		    /** Consecutive: Check if array elements are consecutive
		     * Given an unsorted array of numbers, write a function that returns true if the array consists of consecutive numbers.
		     * Examples: 
		     * 	a) If the array is {5, 2, 3, 1, 4}, then the function should return true because the array has consecutive numbers from 1 to 5.
		     * 	b) If the array is {83, 78, 80, 81, 79, 82}, then the function should return true because the array has consecutive numbers from 78 to 83.
		     * 	c) If the array is {34, 23, 52, 12, 3}, then the function should return false because the elements are not consecutive.
		     * 	d) If the array is {7, 6, 5, 5, 3, 4}, then the function should return false because 5 and 5 are not consecutive.
		     */
		    public boolean callIsConsecutive(int[] arr) {
		    	boolean result = false;
		    	
		    	if (arr.length == 0) {
		    		return result;
		    	}
		    	
		    	int n = arr.length;
		    	
	    	    int min_ele = getMin(arr, n);
	    	    int num = 0;

	    	    for (int i=0; i < n; i++) {
	    	        num ^= min_ele^arr[i];
	    	        min_ele += 1;
	    	    }

	    	    if (num == 0) result = true; 
		    	
		    	return result;
		    }
		    
		    /** Contiguous: Check if array contains contiguous integers with duplicates allowed
		     * Given an array of n integers(duplicates allowed).
		     * 
		     * Input : arr[] = {5, 2, 3, 6, 4, 4, 6, 6}
		     * Output : Yes
		     * The elements form a contiguous set of integers which is {2, 3, 4, 5, 6}.
		     * 
		     * Input : arr[] = {10, 14, 10, 12, 12, 13, 15}
		     * Output : No
		     */
		    public boolean callCheckContiguous(int arr[]) {
		    	if (arr.length == 0) {
		    		return false;
		    	}
		    	
		    	int n = arr.length;

		    	// Find maximum and
		        // minimum elements.
		        int max = Integer.MIN_VALUE;
		        int min = Integer.MAX_VALUE;
		         
		        for (int i = 0; i < n; i++) {
		            max = Math.max(max, arr[i]);
		            min = Math.min(min, arr[i]);
		        }
		      
		        int m = max - min + 1;
		      
		        // There should be at least m elements in array to make them contiguous.
		        if (m > n) return false;

		    	boolean result = true;
		        
		        // Create a visited array and initialize false.
		        boolean  visited[] = new boolean[n];
		      
		        // Mark elements as true.
		        for (int i = 0; i < n; i++)   
		           visited[arr[i] - min] = true;
		      
		        // If any element is not marked, all elements are not contiguous.
		        for (int i = 0; i < m; i++)
		           if (visited[i] == false) result = false;		    	
		    	
		    	return result;
		    }
		}
		
		// 2. Test functions
		TestMethods testMethods = new TestMethods();
		int[] array = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		int[] emptyArray = new int[] {};
		int[] negativeArray = new int[] {-1, -300, -5};
		int[] randomArray = new int[] {-300, -5, 10, 0, 11, 9, 3, 4, 8, 1, 7, 3, 2};

		// callBinarySearch
		assertEquals(testMethods.callBinarySearch(array, 5), 5);
		assertEquals(testMethods.callBinarySearch(array, 0), 0);
		assertEquals(testMethods.callBinarySearch(array, 9), 9);
		
		// callReverseArray
		int[] reverse = testMethods.callRevereArray(array);
		assertEquals(reverse.length, 10);
		assertEquals(reverse[0], 9);
		assertEquals(reverse[9], 0);
		assertEquals(reverse[1], 8);
		assertEquals(reverse[8], 1);
		assertEquals(reverse[2], 7);
		assertEquals(reverse[7], 2);
		assertEquals(reverse[3], 6);
		assertEquals(reverse[6], 3);
		assertEquals(reverse[4], 5);
		assertEquals(reverse[5], 4);

		assertEquals(testMethods.callBinarySearch(array, 10), -1);
		assertEquals(testMethods.callBinarySearch(emptyArray, 5), -1);
		
		// callSmallPositiveInt
		assertEquals(testMethods.callSmallPositiveInt(array), 10);
		assertEquals(testMethods.callSmallPositiveInt(emptyArray), 1);
		assertEquals(testMethods.callSmallPositiveInt(negativeArray), 1);
		assertEquals(testMethods.callSmallPositiveInt(randomArray), 5);
		assertEquals(testMethods.callSmallPositiveInt(new int[] {1, 3, 6, 4, 1, 2}), 5);
		
		// callBinaryGapLength
		assertEquals(testMethods.callBinaryGapLength(0), 0); // 0000b
		assertEquals(testMethods.callBinaryGapLength(1), 0); // 0001b
		assertEquals(testMethods.callBinaryGapLength(5), 1); // 0101b
		assertEquals(testMethods.callBinaryGapLength(9), 2); // 1001b
		assertEquals(testMethods.callBinaryGapLength(529), 4); // 1000010001b
		assertEquals(testMethods.callBinaryGapLength(20), 1); // 10100b
		assertEquals(testMethods.callBinaryGapLength(15), 0); // 1111b
		assertEquals(testMethods.callBinaryGapLength(32), 0); // 100000b
		assertEquals(testMethods.callBinaryGapLength(1041), 5); // 10000010001b
		assertEquals(testMethods.callBinaryGapLength(1074270464), 10); // 1000000000010000001000100000000b
		assertEquals(testMethods.callBinaryGapLength(334121417), 2); // 10011111010100100100111001001b
		assertEquals(testMethods.callBinaryGapLength(334121417), 2); // 10011111010100100100111001001b
		assertEquals(testMethods.callBinaryGapLength(300564937), 5); // 10001111010100100000111001001b
		assertEquals(testMethods.callBinaryGapLength(2147483647), 0); // 11111111111111111111111111111111b
		assertEquals(testMethods.callBinaryGapLength(-529), 0);

		// callIsConsecutive
		assertEquals(testMethods.callIsConsecutive(emptyArray), false);
		assertEquals(testMethods.callIsConsecutive(array), true);
		assertEquals(testMethods.callIsConsecutive(negativeArray), false);
		assertEquals(testMethods.callIsConsecutive(randomArray), false);
		assertEquals(testMethods.callIsConsecutive(new int[] {1, 3, 6, 4, 1, 2}), false);
		assertEquals(testMethods.callIsConsecutive(new int[] {5, 2, 3, 1, 4}), true);
		assertEquals(testMethods.callIsConsecutive(new int[] {83, 78, 80, 81, 79, 82}), true);
		assertEquals(testMethods.callIsConsecutive(new int[] {34, 23, 52, 12, 3}), false);
		assertEquals(testMethods.callIsConsecutive(new int[] {7, 6, 5, 5, 3, 4}), false);
		
		assertEquals(testMethods.callCheckContiguous(new int[] {5, 2, 3, 6, 4, 4, 6, 6}), true);
		assertEquals(testMethods.callCheckContiguous(new int[] {10, 14, 10, 12, 12, 13, 15}), false);
	}
	
	@BeforeClass
	public static void setup() {
		/*
	    mockServerSocket = mock(ServerSocket.class);
	    mockTestClientSocket = mock(Socket.class);

	    try {
	        when(mockServerSocket.accept()).thenReturn(mockTestClientSocket);
	    } catch (IOException e) {
	        fail(e.getMessage());
	    }

	    try {
	        PipedOutputStream oStream = new PipedOutputStream();
	        when(mockTestClientSocket.getOutputStream()).thenReturn(oStream);

	        PipedInputStream iStream = new PipedInputStream(oStream);
	        when(mockTestClientSocket.getInputStream()).thenReturn(iStream);

	        when(mockTestClientSocket.isClosed()).thenReturn(false);
	    } catch (IOException e) {
	        fail(e.getMessage());
	    }
	    */
	}
	
	@AfterClass
	public static void tearDown() {

	}

}
