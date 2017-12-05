package patterns;

public interface ITopic {

	public String getName();
	public ITopic setName(String name);
	public ITopic setAttribute(String attribute, Object value);
	public Object getAttribute(String attribute);
	
}
