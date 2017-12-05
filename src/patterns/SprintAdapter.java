package patterns;

public class SprintAdapter implements Issue {

	private Sprint adapter;
	
	public SprintAdapter(Sprint sprint) {
		adapter = sprint;
	}

	@Override
	public Sprint getAdapter() {
		Sprint result = null;
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
