package edu.virginia.engine.events;

public class CollideEvent extends Event {
	
	public static final String COLLIDE_START = "collided";
	
	public CollideEvent(String eventType, IEventDispatcher source) {
		super(eventType, source);
	}

}
