package patterns;

import java.util.HashMap;

import patterns.Services.*;
import utils.HashListArray;

public class Mediator {

	static Mediator instance = null;
	
	private static HashMap<String, HashListArray<ObserverEvent>> channels = new HashMap<String, HashListArray<ObserverEvent>>();

	public Mediator() {
		if (instance == null) {
			instance = new Mediator();
		}
	}
	
	public static Mediator getInstance() {
		if (instance == null) {
			instance = new Mediator();
		}
		return instance;
	}

	public static void subscribe(String channel, IService service, ObserverEvent event) {

		service.observer = event;

		HashListArray<ObserverEvent> events = null;
		
		if (!channels.containsKey(channel)) {
			events = new HashListArray<ObserverEvent>();
			channels.put(channel, events);
		} else {
			events = channels.get(channel);
		}

		events.add(event);
	}
	
	public static void unsubscribe(String channel, IService service) {
		if (!channels.containsKey(channel)) {
			return;
		}

		HashListArray<ObserverEvent> events = channels.get(channel);

		if (events.contains(service.observer)) {
			events.remove(service.observer);
			events.trimToSize();
			System.out.println("size: " + events.size());
		}
	}
	
	public static void publish(String channel, Task task) {

		if (!channels.containsKey(channel)) {
			return;
		}
		
		HashListArray<ObserverEvent> events = channels.get(channel);
		
		for(int i = 0; i < events.size(); i++) {
			ObserverEvent sub = events.get(i);
			sub.invoke(task);
		}
		
	}
}
