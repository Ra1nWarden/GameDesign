package edu.virginia.engine.tween;

public class ZigZagTweenTransition implements TweenTransition {

	/**
	 * Quadratic equation
	 */
	@Override
	public double applyTransition(double percentDone) {
		return -2 * percentDone * percentDone + 3 * percentDone;
	}

}
