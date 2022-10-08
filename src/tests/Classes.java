package tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.ArrayEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Classes {
	static final Classes CLASSES = new Classes();

	int a = -2000;
	static int b;
	
	interface AA {
	}
	
	interface ABC {
		int a = 0;
		void PrintF();
		void PrintF(AA aa);
	}
	
	abstract class DCE {
		int a;
		ABC abc;

		DCE() {
			this.a=50;
			this.abc = new ABC() {
				int a = 300;
				@Override
				public void PrintF() {
					System.out.printf("ABC inside DCE a=%3d\n", a);
				}
				
				@Override
				public void PrintF(AA aa) {
					
				}
				
			};
			PrintOut();
		}

		DCE(ABC abc) {
			this.a=100;
			this.abc = abc;
			PrintOut();
		}
		
		void PrintOut() {
			System.out.printf("DCE a=%3d\n", a);
			abc.PrintF();
		}
	}
	
	class XYZ extends DCE implements ABC  {
		int a;
		
		XYZ(ABC abc) {
			super(abc);
			a = 10;
			this.abc = abc;
			PrintF();
		}

		@Override
		public void PrintF() {
			System.out.printf("XYZ a=%3d\n", a);
			PrintOut();
			super.PrintOut();
		}

		@Override
		public void PrintF(AA aa) {
		}

	}
	
	class AAA implements AA {
		private ABC abc;
		int value = 10;

		AAA(ABC abc) {
			this.abc = abc;
		}
		
		public void Print() {
			abc.PrintF(this);
		}
	}
	
	private static class Demo extends Thread {
		public static String Title = "";

		public Demo(String t) {
			synchronized(Title) {
				Title = t;
			}
		}

		public static void showTitle() {
			synchronized(Title) {
				System.out.println("Title is " + Title);
			}
		}
	}
	
	final public static class DerivedDemo extends Demo implements Runnable {

		public DerivedDemo(String t) {
			super(t);
		}

		public static void setTitle(String t) {
			synchronized(Title) {
				Title = t;
			}
		}
		
		@Override
		public synchronized void run() {
			showTitle();
			setTitle("NEW TITLE");
			showTitle();
		}
		
	}
	
	public static class Widget {
		static private int widgetCount = 0;
		private String wName;
		private int wNumber;
		
		private static synchronized int addWidget() {
			return ++widgetCount;
		}
		
		public Widget() {
			wNumber = addWidget();
		}
		
		public int getNumber() {
			return wNumber;
		}
	}

	@Test
	public void classDelegates_thenEquals() {

		new AAA(new ABC() {

			@Override
			public void PrintF(AA self) {
				System.out.println("(1) = " + ((AAA) self).value);
			}

			@Override
			public void PrintF() {
				
			}
			
		}).Print();
		
		assertEquals(5, b);
	}

	@Test
	public void classInheritance_thenEquals() {
		
		Classes self = this;
		
		XYZ xyz = new XYZ(new ABC() {
			@Override
			public void PrintF() {
				System.out.printf("ABC a=%3d\n", a);
				System.out.printf("DateConvertTest a=%3d\n", self.a);
			}

			@Override
			public void PrintF(AA aa) {
			}
		});

		System.out.printf("outside a=%3d\n", xyz.a);
		xyz.abc.PrintF();
		assertEquals(xyz.a, 10);
		assertEquals(ABC.a, 0);
	}

	static {
		b = 5;
	}
	
	@Test
	public void classSynchronize_thenEquals() {

		assertEquals(-2000, a);

		this.a = 1000;
		
		new Thread(new Runnable() {
			public void run() {
				System.out.println("(1) = " + a);
				a=30000;
			}
		}).start();

		synchronized(this) {
			this.a = 5;
		}

		new Thread(new Runnable() {
			public void run() {
				System.out.println("(2) = " + a);
			}
		}).start();
		
		System.out.println("(3) = " + a);
		
		assertEquals(30000, a);
		assertEquals(5, b);
	}

	@Test
	public void testCollections_thenEquals() {

		assertEquals(1, 1);
	}
	
	@Test
	public void testDerivedDemo() {
		final DerivedDemo dDemo1 = new DerivedDemo("TEST1");
		dDemo1.start();
		final DerivedDemo dDemo2 = new DerivedDemo("TEST2");
		dDemo2.start();
	}
	
	@Test
	public void testWidget() {
		List<Thread> threads = new ArrayList<>();
		List<Integer> ids = new ArrayList<>();
		
		for (int i=0; i<100; i++) {
			threads.add(new Thread(new Runnable() {
				@Override
				public void run() {
					Widget widget = new Widget();
					ids.add(widget.getNumber());
				}
				
			}));
		}
		
		if (threads.size() > 0) {
			threads.forEach((Thread thread) -> {
				if (thread != null) {
					thread.start();
				}
			});
		}

		ids.forEach((Integer id) -> {
			if (id != null) {
				System.out.println(id.intValue());
			}
		});
	}
	
	/**
	 * The Sealed Classes
	 * 
	 * A sealed class imposes three important constraints on its permitted subclasses:
	 * - All permitted subclasses must belong to the same module as the sealed class.
	 * - Every permitted subclass must explicitly extend the sealed class.
	 * - Every permitted subclass must define a modifier: final, sealed, or non-sealed.
	 * 
	 *
	 */

	// Declare Sealed classes and interfaces for Tests
	public sealed interface Service permits Car, Truck {

	    int getMaxServiceIntervalInMonths();

	    default int getMaxDistanceBetweenServicesInKilometers() {
	        return 100000;
	    }
	}

	// The permits clause should be defined after any extends or implements clauses
	public abstract sealed class Vehicle permits Car, Truck {

	    private final String registrationNumber;

	    public Vehicle(String registrationNumber) {
	        this.registrationNumber = registrationNumber;
	    }

	    public String getRegistrationNumber() {
	        return registrationNumber;
	    }
	}
	
	// A permitted subclass may also be declared sealed
	public final class Truck extends Vehicle implements Service {

	    private final int loadCapacity;

	    public Truck(int loadCapacity, String registrationNumber) {
	        super(registrationNumber);
	        this.loadCapacity = loadCapacity;
	    }

	    public int getLoadCapacity() {
	        return loadCapacity;
	    }

	    @Override
	    public int getMaxServiceIntervalInMonths() {
	        return 18;
	    }
	}

	// Declare it non-sealed, then it is open for extension
	public final non-sealed class Car extends Vehicle implements Service {

	    private final int numberOfSeats;

	    public Car(int numberOfSeats, String registrationNumber) {
	        super(registrationNumber);
	        this.numberOfSeats = numberOfSeats;
	    }

	    public int getNumberOfSeats() {
	        return numberOfSeats;
	    }

	    @Override
	    public int getMaxServiceIntervalInMonths() {
	        return 12;
	    }
	}
	
	// Using Records
	public sealed interface VehicleRecord permits CarRecord, TruckRecord {

	    String getRegistrationNumber();

	}

	public record CarRecord(int numberOfSeats, String registrationNumber) implements VehicleRecord {

	    @Override
	    public String getRegistrationNumber() {
	        return registrationNumber;
	    }

	    public int getNumberOfSeats() {
	        return numberOfSeats;
	    }

	}

	public record TruckRecord(int loadCapacity, String registrationNumber) implements VehicleRecord {

	    @Override
	    public String getRegistrationNumber() {
	        return registrationNumber;
	    }

	    public int getLoadCapacity() {
	        return loadCapacity;
	    }

	}

	// Test the Sealed-Classes and Records Inheritance
	@Test
	public void sealedClassInheritance() {

		// Implementation of the Sealed Classes
		Car cargen = CLASSES.new Car(5, "12345");
		Truck trackgen = CLASSES.new Truck(3000, "98765");

		assertEquals(cargen.getNumberOfSeats(), 5);
		assertEquals(cargen.getMaxServiceIntervalInMonths(), 12);

		assertEquals(trackgen.getLoadCapacity(), 3000);
		assertEquals(trackgen.getMaxServiceIntervalInMonths(), 18);

		// Then pattern Matching
		Vehicle vehicle1 = cargen;
		
		if (vehicle1 instanceof Car car) {
			assertEquals(car.getNumberOfSeats(), 5);
		} else if (vehicle1 instanceof Truck truck) {
			fail("Wrong instance of Vehicle");
		} else {
			fail("Unknown instance of Vehicle");
		}

		Vehicle vehicle2 = trackgen;
		
		if (vehicle2 instanceof Car car) {
			fail("Wrong instance of Vehicle");
		} else if (vehicle2 instanceof Truck truck) {
			assertEquals(truck.getLoadCapacity(), 3000);
		} else {
			fail("Unknown instance of Vehicle");
		}
		
		// Implementation of the Sealed Records
		CarRecord cargenrec = new CarRecord(5, "12345");
		TruckRecord trackgenrec = new TruckRecord(3000, "98765");

		assertEquals(cargenrec.getNumberOfSeats(), 5);
		assertEquals(trackgenrec.getLoadCapacity(), 3000);
		
		// Test Reflections
		assertFalse(trackgen.getClass().isSealed());
		assertTrue(trackgen.getClass().getSuperclass().isSealed());
		
		boolean found = false;

		for (var subclass : trackgen.getClass().getSuperclass().getPermittedSubclasses()) {
			if (subclass.getCanonicalName() == trackgen.getClass().getCanonicalName()) {
				found = true;
				break;
			}
	      }
		
		assertTrue(found);
	}

	@BeforeClass	
	public static void setup() {

	}

	@AfterClass
	public static void tearDown() {
		
	}

}
