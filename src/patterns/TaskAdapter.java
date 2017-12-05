package patterns;

public class TaskAdapter implements Issue  {
	
	private Task adapter;
	
	public TaskAdapter(Task task) {
		adapter = task;
	}

	@Override
	public Task getAdapter() {
		Task result = null;
		if (adapter != null) {
			try {
				result = adapter.getClone();
			} catch (CloneNotSupportedException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public void print() {
		if (adapter != null) {
			System.out.print(adapter.toString());
		}
	}

}
