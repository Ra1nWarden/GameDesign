package edu.virginia.engine.tween;

import java.util.ArrayList;
import java.util.List;

public class TweenJuggler {
	
	private static TweenJuggler instance;
	private List<Tween> tweens;
	
	private TweenJuggler() {
		tweens = new ArrayList<Tween>();
	}
	
	public static TweenJuggler getInstance() {
		if(instance == null) {
			instance = new TweenJuggler();
		}
		return instance;
	}
	
	public void addTween(Tween tween) {
		tweens.add(tween);
	}
	
	public void nextFrame() {
		List<Tween> tweensCopy = new ArrayList<Tween>();
		List<Tween> removeTween = new ArrayList<Tween>();
		tweensCopy.addAll(tweens);
		for(Tween tween : tweensCopy) {
			if(tween.isComplete()) {
				tween.dispatchEvent(new TweenEvent(TweenEvent.TWEEN_COMPLETE_EVENT, tween));
				removeTween.add(tween);
			} else {
				tween.update();
			}
		}
		tweens.removeAll(removeTween);
	}

}
