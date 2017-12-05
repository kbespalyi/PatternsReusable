package patterns;

public abstract class HelpHandler {

	private HelpHandler _parent;
	private Topic _topic;
	
	public HelpHandler() {
		_parent = null;
		_topic = Topic.TOP_TOPIC;
	}

	public HelpHandler(HelpHandler s, Topic t) {
		_parent = s;
		_topic = t;
	}

	public HelpHandler(Topic t) {
		_topic = t;
	}

	public void setHandler(HelpHandler s, Topic t) {
		_parent = s;
		_topic = t;
	}

	public HelpHandler getHandler() {
		return _parent;
	}

	public Topic getTopic() {
		return _topic;
	}
	
	public HelpHandler handleHelp() {
		return _parent != null ? _parent.handleHelp() : this; 
	}
	
	public boolean hasHelp() {
		return _topic != Topic.TOP_TOPIC && _parent != null;
	}
	
	public static enum Topic {
		TOP_TOPIC,
		TASK_TOPIC,
		EPIC_TOPIC;
		
		@Override
		public String toString() {
			return "\"" + this.name() + "\"";
		}
	}

	@Override
	public String toString() {
		return "helpHandler: {\ntopic: " + getTopic().toString() + ",\nhas_helper: " + hasHelp() + "\n}";
	}
	
}
