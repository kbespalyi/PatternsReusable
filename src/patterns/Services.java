package patterns;

public final class Services {

	public interface ObserverEvent {
		public void invoke(Task context);
	}
	
	public static abstract class IService {
		public ObserverEvent observer = null;

		public abstract void update(Task task);
	}
	
	public static class NotificationService extends IService {
		final private String message = "Nothing ";

		@Override
		public void update(Task task) {
			System.out.println(message + task.getUser() + " for task " + task.getName());
		}
	}

	public static class LoggingService extends IService {
		final private String message = "Logging ";

		@Override
		public void update(Task task) {
			System.out.println(message + task.getUser() + " for task " + task.getName());
		}
	}

	public static class AuditingService extends IService {
		final private String message = "Auditiong ";

		@Override
		public void update(Task task) {
			System.out.println(message + task.getUser() + " for task " + task.getName());
		}
	}
}
