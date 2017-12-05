package patterns;

public abstract class State {

	final private Chain.Statuses KEY = Chain.Statuses.OFF;
	
    public void pull(Chain wrapper) {
        wrapper.setState(new Chain.Off());
        System.out.println("   turning off");
    }
    
    public Chain.Statuses getState() {
    	return KEY;
    }

}
