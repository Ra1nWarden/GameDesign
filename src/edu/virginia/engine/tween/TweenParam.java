package edu.virginia.engine.tween;

public class TweenParam {

	private TweenableParam param;
	private double startVal;
	private double endVal;
	private double time;

	public TweenParam(TweenableParam param, double startVal, double endVal, double time) {
		this.param = param;
		this.startVal = startVal;
		this.endVal = endVal;
		this.time = time;
	}

	public TweenableParam getParam() {
		return param;
	}

	public double getStartVal() {
		return startVal;
	}

	public double getEndVal() {
		return endVal;
	}

	public double getTime() {
		return time;
	}

}
