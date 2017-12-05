package patterns;

import patterns.TaskProperties;
import utils.HashList;
import utils.HashListArray;

public class TaskFlyweightFactory {
	
	private static final HashList<FlyweightTask> flyweights = new HashList<FlyweightTask>();
	private static final HashListArray<FlyweightTask> flyweights_ = new HashListArray<FlyweightTask>();

	public static FlyweightTask get(String project, int priority, String user, boolean completed) {

		int hashCode = getHashCode(project, priority, user, completed);
		
		FlyweightTask item = flyweights.findByHash(hashCode);
		if (item == null) {
			item = flyweights.addItem(new FlyweightTask(project, priority, user, completed));
		}
		return item;
	}

	// HashMapList
	public static FlyweightTask get(TaskProperties properties) {

		int hashCode = getHashCode(properties.project, properties.priority, properties.user, properties.completed);
		
		FlyweightTask item = flyweights.findByHash(hashCode);
		if (item == null) {
			item = flyweights.addItem(new FlyweightTask(properties));
		}
		return item;
	}

	// HashListArray
	public static FlyweightTask get_(TaskProperties properties) {

		FlyweightTask item;
		
		int hashCode = getHashCode(properties.project, properties.priority, properties.user, properties.completed);
		
		int index = flyweights_.findByHash(hashCode);
		if (index == -1) {
			item = flyweights_.push(new FlyweightTask(properties));
		} else {
			item = flyweights_.get(index);
		}
		return item;
	}
	
	public static void clear() {
		flyweights.clear();
		flyweights_.clear();
	}

	public static int getCount() {
		return Math.max(flyweights.size(), flyweights_.size());
	}
	
	private static int getHashCode(String project, int priority, String user, boolean completed) {
		final int prime = 31;
		int result = 1;
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + priority;
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	public static class FlyweightTask {

		private String project;
		private int priority;
		private String user;
		private boolean completed;

		public FlyweightTask(String project, int priority, String user, boolean completed) {
			this.project = project;
			this.priority = priority;
			this.user = user;
			this.completed = completed;
		}

		public FlyweightTask(TaskProperties properies) {
			this.project = properies.project;
			this.priority = properies.priority;
			this.user = properies.user;
			this.completed = properies.completed;
		}
		
		public String getProject() {
			return this.project;
		}

		public void setProject(String project) {
			this.project = project;
		}

		public int getPriority() {
			return this.priority;
		}

		public void setPriority(int priority) {
			this.priority = priority;
		}

		public String getUser() {
			return this.user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public boolean isCompleted() {
			return this.completed;
		}

		public void setCompleted(boolean completed) {
			this.completed = completed;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (this.completed ? 1231 : 1237);
			result = prime * result + this.priority;
			result = prime * result + ((this.project == null) ? 0 : this.project.hashCode());
			result = prime * result + ((this.user == null) ? 0 : this.user.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof FlyweightTask)) {
				return false;
			}
			FlyweightTask other = (FlyweightTask) obj;
			if (this.completed != other.completed) {
				return false;
			}
			if (this.priority != other.priority) {
				return false;
			}
			if (this.project == null) {
				if (other.project != null) {
					return false;
				}
			} else if (!this.project.equals(other.project)) {
				return false;
			}
			if (this.user == null) {
				if (other.user != null) {
					return false;
				}
			} else if (!this.user.equals(other.user)) {
				return false;
			}
			return true;
		}
		
		public FlyweightTask getClone() throws CloneNotSupportedException {
			return (FlyweightTask) this.clone();
		}
		
	}
	
}
