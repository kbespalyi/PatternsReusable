package patterns;

import java.util.ArrayList;
import java.util.List;

final public class VisitorStacks {

	public static class Leaf implements Component {
	    private int number;

	    public Leaf(int value) {
	        this.number = value;
	    }

	    public void traverse() {
	        System.out.print(number + " ");
	    }
	}

	public static class Composite implements Component {
	    private static char next = 'a';
	    private List<Component> children = new ArrayList<Component>();
	    private char letter = next++;

	    public void add(Component c) {
	        children.add(c);
	    }

	    public void traverse() {
	        System.out.print(letter + " ");
	        for (Object aChildren : children) {
	            ((Component) aChildren).traverse();
	        }
	    }
	}
}
