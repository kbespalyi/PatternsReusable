package patterns;

public interface IJob {
	public boolean hasJob();
	public IJob up();
	public void start();
	public void reset();
	public Task getTask();
	public Sprint getSpring();
	public Epic getEpic();
	public Chain.Statuses getPosition();
}
