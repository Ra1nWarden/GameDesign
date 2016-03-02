package edu.virginia.engine.tween;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventDispatcher;
import edu.virginia.engine.events.IEventListener;

public class Tween implements IEventDispatcher {

	private DisplayObject obj;
	private List<TweenParam> tweenParams;
	private TweenTransition transition;
	private long startTime;
	private HashMap<String, List<IEventListener>> eventListeners;

	public Tween(DisplayObject obj) {
		this.obj = obj;
		tweenParams = new ArrayList<TweenParam>();
		this.transition = new LinearTweenTransition();
		this.startTime = System.currentTimeMillis();
		eventListeners = new HashMap<String, List<IEventListener>>();
	}

	public Tween(DisplayObject obj, TweenTransition transition) {
		this.obj = obj;
		this.transition = transition;
		tweenParams = new ArrayList<TweenParam>();
		eventListeners = new HashMap<String, List<IEventListener>>();
		this.startTime = System.currentTimeMillis();
	}

	public void animate(TweenableParam param, double startVal, double endVal, double time) {
		tweenParams.add(new TweenParam(param, startVal, endVal, time));
	}

	public void update() {
		long currentTime = System.currentTimeMillis();
		for (TweenParam tweenParam : tweenParams) {
			if (currentTime - startTime >= tweenParam.getTime()) {
				continue;
			}
			double percent = transition.applyTransition((double) (currentTime - startTime) / tweenParam.getTime());
			switch (tweenParam.getParam()) {
			case X:
				obj.setxPosition((int) (tweenParam.getStartVal()
						+ (percent * (tweenParam.getEndVal() - tweenParam.getStartVal()))));
				break;
			case Y:
				obj.setyPosition((int) (tweenParam.getStartVal()
						+ (percent * (tweenParam.getEndVal() - tweenParam.getStartVal()))));
				break;
			case SCALE_X:
				obj.setScaleX((float) tweenParam.getStartVal()
						+ (float) (percent * (tweenParam.getEndVal() - tweenParam.getStartVal())));
				break;
			case SCALE_Y:
				obj.setScaleY((float) tweenParam.getStartVal()
						+ (float) (percent * (tweenParam.getEndVal() - tweenParam.getStartVal())));
				break;
			case ALPHA:
				obj.setAlpha((float) tweenParam.getStartVal()
						+ (float) (percent * (tweenParam.getEndVal() - tweenParam.getStartVal())));
				break;
			}
		}
	}

	public boolean isComplete() {
		long currentTime = System.currentTimeMillis();
		for (TweenParam tweenParam : tweenParams) {
			if (tweenParam.getTime() > currentTime - startTime) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void addEventListener(IEventListener listener, String eventType) {
		if (eventListeners.containsKey(eventType)) {
			eventListeners.get(eventType).add(listener);
		} else {
			List<IEventListener> listeners = new ArrayList<IEventListener>();
			listeners.add(listener);
			eventListeners.put(eventType, listeners);
		}

	}

	@Override
	public void removeEventListener(IEventListener listener, String eventType) {
		if (eventListeners.containsKey(eventType)) {
			eventListeners.get(eventType).remove(listener);
		}
	}

	@Override
	public void dispatchEvent(Event event) {
		String eventType = event.getEventType();
		if (eventListeners.containsKey(eventType)) {
			for (IEventListener each : eventListeners.get(eventType)) {
				each.handleEvent(event);
			}
		}
	}

	@Override
	public boolean hasEventListener(IEventListener listener, String eventType) {
		if (eventListeners.containsKey(eventType)) {
			return eventListeners.get(eventType).contains(listener);
		} else {
			return false;
		}
	}

}
