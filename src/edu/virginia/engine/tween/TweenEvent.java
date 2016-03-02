package edu.virginia.engine.tween;

import edu.virginia.engine.events.Event;

public class TweenEvent extends Event {
	
	public static final String TWEEN_COMPLETE_EVENT = "TweenComplete";
	
	private Tween tween;
	private String eventType;
	
	public TweenEvent(String eventType, Tween tween) {
		super(eventType, tween);
		this.tween = tween;
		this.eventType = eventType;
	}
	
	public Tween getTween() {
		return tween;
	}

}
