package edu.virginia.engine.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventDispatcher implements IEventDispatcher {
	
	protected HashMap<String, List<IEventListener> > eventListeners;

	@Override
	public void addEventListener(IEventListener listener, String eventType) {
		if(eventListeners.containsKey(eventType)) {
			eventListeners.get(eventType).add(listener);
		} else {
			List<IEventListener> listeners = new ArrayList<IEventListener>();
			listeners.add(listener);
			eventListeners.put(eventType, listeners);
		}
		
	}

	@Override
	public void removeEventListener(IEventListener listener, String eventType) {
		if(eventListeners.containsKey(eventType)) {
			eventListeners.get(eventType).remove(listener);
		}
	}

	@Override
	public void dispatchEvent(Event event) {
		String eventType = event.getEventType();
		if(eventListeners.containsKey(eventType)) {
			for(IEventListener each : eventListeners.get(eventType)) {
				each.handleEvent(event);
			}
		}
	}

	@Override
	public boolean hasEventListener(IEventListener listener, String eventType) {
		if(eventListeners.containsKey(eventType)) {
			return eventListeners.get(eventType).contains(listener);
		} else {
			return false;
		}
	}

}
