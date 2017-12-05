package patterns;

public final class StrategyStacks {

	public abstract static class StrategySolution implements Strategy {
	    // Template Method
	    public void solve() {
	        start();
	        while (nextTry() && !isSolution()) {}
	        stop();
	    }

	    protected abstract void start();
	    protected abstract boolean nextTry();
	    protected abstract boolean isSolution();
	    protected abstract void stop();
	}
	
	public abstract static class StrategySearch implements Strategy {
	    // Template Method
	    public void solve() {
	        while (true) {
	            preProcess();
	            if (search()) {
	                break;
	            }
	            postProcess();
	        }
	    }

	    protected abstract void preProcess();
	    protected abstract boolean search();
	    protected abstract void postProcess();
	}
	
	// Command method - execute client behavior 
    public static void execute(Strategy strategy) {
        strategy.solve();
    }
}
