package patterns;

public class Chain {

	public static enum Statuses {
		OFF, LOW, MEDIUM, HIGH
	}

	private State current;

    public Chain() {
        current = new Off();
    }

    public void setState(State state) {
        current = state;
    }

    public Statuses getState() {
        return current.getState();
    }
    
    public void pull() {
        current.pull(this);
    }
    
    public void print() {
        System.out.println("   " + getState().toString() + " speed");
    }
    
    public static class Off extends State {
    	
    	final private Statuses KEY = Statuses.OFF;

    	@Override
        public void pull(Chain wrapper) {
            wrapper.setState(new Low());
            wrapper.print();
        }
    	
    	@Override
    	public Statuses getState() {
        	return KEY;
        }

    }

    public static class Low extends State {

    	final private Statuses KEY = Statuses.LOW;

    	@Override
        public void pull(Chain wrapper) {
            wrapper.setState(new Medium());
            wrapper.print();
        }

    	@Override
    	public Statuses getState() {
        	return KEY;
        }
    }

    public static class Medium extends State {

    	final private Statuses KEY = Statuses.MEDIUM;
    	
    	@Override
        public void pull(Chain wrapper) {
            wrapper.setState(new High());
            wrapper.print();
        }

    	@Override
    	public Statuses getState() {
        	return KEY;
        }
    }

    public static class High extends State {

    	final private Statuses KEY = Statuses.HIGH;

    	@Override
    	public Statuses getState() {
        	return KEY;
        }
    }

}
