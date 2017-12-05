package patterns;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

final public class PrivateClassData {
	private static final int CANVAS_WIDTH = 1000;
	private static final int CANVAS_HEIGHT = 1000;

	// Attributes of moving object
	private static int x = 100; // top-left (x, y)
	private static int y = 100;
	private static int size = 150; // width and height
	private static int xSpeed = 3, ySpeed = 5; // displacement per step in x,y
	public static boolean isExit = false;

	static public class IColor extends Color {
		private static final long serialVersionUID = 1265564778363845994L;
		private String color = "";

		public IColor(String color) {
			super(Color.decode(color).getRGB());
			this.color = color;
		}

		public String getColor() {
			return color;
		}
	}

	static public class IPoint extends Point {

		private static final long serialVersionUID = -8081004382469814096L;

		public IPoint(int x, int y) {
			super(x, y);
		}

	}

	static public class CircleData {
		private double radius;
		private IColor color;
		private Point origin;

		public CircleData(double radius, IColor color, Point origin) {
			this.radius = radius;
			this.color = color;
			this.origin = origin;
		}

		public double getRadius() {
			return this.radius;
		}

		public Color getColor() {
			return this.color;
		}

		public Point getPoint() {
			return this.origin;
		}
	}

	static public class Circle {
		private CircleData circleData;

		public Circle(double radius, IColor color, Point origin) {
			this.circleData = new CircleData(radius, color, origin);
		}

		public double getCircumference() {
			return this.circleData.getRadius() * Math.PI;
		}

		public double getDiameter() {
			return this.circleData.getRadius() * 2;
		}

		public void Draw(Graphics graphics) {
			graphics.setPaintMode();
			graphics.setColor(this.circleData.getColor());
			Point point = this.circleData.getPoint();
			double diameter = getDiameter();
			graphics.drawOval(point.x, point.y, (int) diameter, (int) diameter);
		}
	}

	public static JFrame buildFrame() {
		JFrame frame = new JFrame("ImageDrawing");
		frame.pack();
		frame.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				isExit = true;
				System.exit(0);
			}
		});

		JPanel canvas2D = new JPanel() {
			private static final long serialVersionUID = -5802052015702613765L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				Circle circle = new Circle(size, new IColor("#FF00FF"), new IPoint(x, y));
				BufferedImage bi = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);
				circle.Draw(bi.getGraphics());
				
				g.drawImage(bi, 0, 0, null);
			}
		};

		frame.add(canvas2D);
		frame.setVisible(true);
		return frame;
	}

	private static final int UPDATE_INTERVAL = 50;

	public static void start(JFrame frame) {

		// Create a new thread to run update at regular interval
		Thread updateThread = new Thread() {
			@Override
			public void run() {
				while (true) {
					update(); // update the (x, y) position
					frame.repaint(); // Refresh the JFrame. Called back
								// paintComponent()
					try {
						// Delay and give other thread a chance to run
						Thread.sleep(UPDATE_INTERVAL); // milliseconds
					} catch (InterruptedException ignore) {
					}
				}
			}
		};
		updateThread.start(); // called back run()
	}

	// Update the (x, y) position of the moving object
	static public void update() {
		x += xSpeed;
		y += ySpeed;
		if (x > CANVAS_WIDTH - size || x < 0) {
			xSpeed = -xSpeed;
		}
		if (y > CANVAS_HEIGHT - size || y < 0) {
			ySpeed = -ySpeed;
		}
	}
}
