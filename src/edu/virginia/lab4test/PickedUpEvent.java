package edu.virginia.lab4test;

import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventDispatcher;

public class PickedUpEvent extends Event {
	
	static final String COIN_PICKED_UP = "picked";
	
	public PickedUpEvent(String eventType, IEventDispatcher source) {
		super(eventType, source);
	}

}
