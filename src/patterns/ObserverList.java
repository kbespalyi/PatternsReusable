package patterns;

import patterns.Services.ObserverEvent;
import utils.HashListArray;

public class ObserverList {

	private HashListArray<ObserverEvent> observerList = new HashListArray<ObserverEvent>();
	
	ObserverList() {}
	
	public ObserverEvent add(ObserverEvent event) {
		return observerList.push(event);
	}

	public ObserverEvent get(int index) {
		return observerList.get(index);
	}
	
	public void removeAt(int index) {
		if (index > -1 && index < observerList.size()) {
			observerList.remove(index);
		}
	}
	
	public int indexOf(ObserverEvent obj, int startIndex) {
		int result = -1;
		int i = startIndex;
		while (i < observerList.size()) {
			if (observerList.get(i).equals(obj)) {
				result = i;
				break;
			}
			i++;
		}
		return result;
	}

	public int count() {
		return observerList.size();
	}

}
