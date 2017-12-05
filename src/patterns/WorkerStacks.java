package patterns;

public final class WorkerStacks {

	// Templates
	public static abstract class Worker {
		
		protected String name = null;

		public final void doDayWorkflow() {
			whois();
			getUp();
			eatBreakfast();
			gotoWork();
			dailyRoutine();
			returnToHome();
			work();
			relax();
			sleep();
	    }

		private void dailyRoutine() {
	        System.out.print("Daily Routine  ");
		}
		private void getUp() {
	        System.out.print("Get Up  ");
		}
		private void eatBreakfast() {
	        System.out.print("Eat Breakfast  ");
		}
		private void gotoWork() {
	        System.out.print("Goto Work  ");
		}
		private void returnToHome() {
	        System.out.print("Return to Home  ");
		}
		private void sleep() {
	        System.out.print("Sleep  \n");
		}

		protected void relax() {
			
		}

		protected abstract void work();

		protected void whois() {
	        System.out.print(name + ": ");
		}

		public String getName() {
			return this.name;
		}

	}
	
	public static class FireFighter extends Worker {

		public FireFighter() {
			super.name = "FireFighter";
		}

		@Override
		protected void work() {
	        System.out.print("Work So Hard  ");
		}
	}

	public static class Lumberjack extends Worker {

		public Lumberjack() {
			super.name = "Lumberjack";
		}

		@Override
		protected void work() {
	        System.out.print("Work Hard  ");
		}
	}

	public static class Postman extends Worker {

		public Postman() {
			super.name = "Postman";
		}

		@Override
		protected void work() {
	        System.out.print("Work Medium  ");
		}
}

	public static class Manager extends Worker {

		public Manager() {
			super.name = "Manager";
		}

		@Override
		protected void work() {
	        System.out.print("Work Easy  ");
		}

		@Override
		protected void relax() {
	        System.out.print("Relax Well  ");
		}

	}
}
