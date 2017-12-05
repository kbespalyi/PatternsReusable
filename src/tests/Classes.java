package tests;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class Classes {
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

	@BeforeClass	
	public static void setup() {

	}

	@AfterClass
	public static void tearDown() {
		
	}

}
