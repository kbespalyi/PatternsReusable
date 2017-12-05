package patterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Options implements ITopic {

	private ITopic chain;
	private HashMap<String, Object> options = new HashMap<>();
	private List<String> path = new ArrayList<>();
	
	public Options(ITopic chain) {
		this.chain = chain;
	}
	
	public ITopic set(ITopic chain) {
		this.chain = chain;
		return chain;
	}
	
	@Override
	public ITopic setAttribute(String attribute, Object value) {
		if (!options.containsKey(attribute) || !options.get(attribute).equals(value)) {

			System.out.println("put " + value + " to " + attribute);
			options.put(attribute, value);

			if (path.contains(attribute)) {
				path.remove(attribute);
			}

			if (chain != null) {
				return chain.setAttribute(attribute, value);
			}
		}
		return this;
	}

	@Override
	public Object getAttribute(String attribute) {
		if (options.containsKey(attribute)) {
			System.out.println("get value " + options.get(attribute) + " from " + attribute);
			return options.get(attribute);
		} else if (chain != null && !path.contains(attribute)) {
			System.out.println(attribute + " attribute not found");
			path.add(attribute);
			return chain.getAttribute(attribute);
		} else {
			return null;
		}
	}

	@Override
	public String getName() {
		return (String) getAttribute("name");
	}

	@Override
	public ITopic setName(String name) {
		return setAttribute("name", name);
	}

}
