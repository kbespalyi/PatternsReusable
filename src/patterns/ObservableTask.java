package patterns;

import patterns.Services.*;

public class ObservableTask extends Task {

	ObserverList observers;
	
	public ObservableTask(String name, TaskProperties properties) {
		super(name, properties);
		this.observers = new ObserverList();
	}

	public ObserverEvent addObserver(IService service, ObserverEvent observer) {
		service.observer = observer;
		return this.observers.add(observer);
	}

	public void notify(Task context) {
		int observerCount = this.observers.count();
		for (int i=0; i < observerCount; i++) {
			this.observers.get(i).invoke(context); // -> invoke update method from services
		}
	}
	
	public void save() {
		super.save();
		this.notify(this);
	}

	public void removeObserver(ObserverEvent observer) {
		this.observers.removeAt(this.observers.indexOf(observer, 0));
	}

}
