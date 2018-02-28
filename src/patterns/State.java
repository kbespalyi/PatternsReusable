package patterns;

public abstract class State {

	final private Chain.Statuses KEY = Chain.Statuses.OFF;
	
    public void pull(Chain wrapper) {
        wrapper.setState(new Chain.Off());
    }
    
    public Chain.Statuses getState() {
    	return KEY;
    }

}
