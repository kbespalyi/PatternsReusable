package patterns;

public final class Services {

	public interface ObserverEvent {
		public void invoke(Task context);
	}
	
	public static abstract class IService {
		protected String message = "";
		public ObserverEvent observer = null;

		public String getMessage() {
			return message;
		}

		public abstract void update(Task task);
	}
	
	public static class NotificationService extends IService {

		public NotificationService() {
			message = "Notifications";
		}

		@Override
		public void update(Task task) {
			task.update(message);
			//System.out.println(message + ": " + task.getUser() + " for task " + task.getName());
		}
	}

	public static class LoggingService extends IService {

		public LoggingService() {
			message = "Loggings";
		}

		@Override
		public void update(Task task) {
			task.update(message);
			//System.out.println(message + ": " + task.getUser() + " for task " + task.getName());
		}
	}

	public static class AuditingService extends IService {

		public AuditingService() {
			message = "Auditings";
		}

		@Override
		public void update(Task task) {
			task.update(message);
			//System.out.println(message + ": " + task.getUser() + " for task " + task.getName());
		}
	}
}
