package patterns;

final public class NullObjectStacks {

	interface ListVisitor {
	    Object whenNonNullList(NonNullList host, Object param);
	    Object whenNullList(NullList host, Object param);
	}

	static abstract class List {
	    public abstract List getTail();
	    public abstract Object accept(ListVisitor visitor, Object param);
	}
	
	static class NonNullList extends List {
	    private Object head;
	    private List tail;

	    // Creates a list from a head and tail. Acts as "cons"
	    public NonNullList(Object head, List tail) {
	        this.head = head;
	        this.tail = tail;
	    }
	    // for convenience we could add a constructor taking only the head to make 1 element lists.
	    public Object getHead() {
	        return head;
	    }

	    public List getTail() {
	        return tail;
	    }

	    public Object accept(ListVisitor visitor, Object param) {
	        return visitor.whenNonNullList(this, param);
	    }
	}
	
	static class NullList extends List {
	    private static NullList instanceObj = new NullList();
	    
	    public NullList singleton() {
	        return instanceObj;
	    }

	    public List getTail() {
	        return this;
	    }

	    public Object accept(ListVisitor visitor, Object param) {
	        return visitor.whenNullList(this, param);
	    }
	}
}
