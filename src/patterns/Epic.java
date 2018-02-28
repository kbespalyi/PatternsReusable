package patterns;

public class Epic extends HelpHandler implements ITopic, Cloneable {

	private String name;

	public Epic(String name) {
		super(Topic.TOP_TOPIC);
		this.name = name;
	}

	public Epic(HelpHandler s, String name) {
		super(s, Topic.EPIC_TOPIC);
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Epic setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
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
		if (!(obj instanceof Epic)) {
			return false;
		}
		Epic other = (Epic) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
	public Epic getClone() throws CloneNotSupportedException {
		return (Epic) super.clone();
	}

	@Override
	public Epic clone() {
		Epic clone = null;
		try {
			clone = this.getClone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}
	
	@Override
	public ITopic setAttribute(String attribute, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(String attribute) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
