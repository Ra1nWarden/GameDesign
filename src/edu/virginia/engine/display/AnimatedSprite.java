package edu.virginia.engine.display;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimatedSprite extends Sprite {

	private List<BufferedImage> frames = new ArrayList<BufferedImage>();
	private Map<String, List<Integer>> animations = new HashMap<String, List<Integer>>();
	private long cycleTimeInNanoSec = 1000000000;
	private long startTime;
	private boolean isPlaying;
	private String animationName;

	public AnimatedSprite(String id, List<String> fileNames) {
		super(id);
		for (String fileName : fileNames) {
			BufferedImage image = this.readImage(fileName);
			if (image != null) {
				frames.add(image);
			}
		}
		isPlaying = true;
		startTime = System.nanoTime();
	}

	public void addAnimationSet(String name, List<Integer> indices) {
		animations.put(name, indices);
	}

	public boolean setAnimation(String name) {
		if (animations.containsKey(name)) {
			animationName = name;
			return true;
		} else {
			return false;
		}
	}

	public long getCycleTimeInNanoSec() {
		return cycleTimeInNanoSec;
	}

	public void setCycleTimeInNanoSec(long cycleTime) {
		this.cycleTimeInNanoSec = cycleTime;
	}

	public boolean getIsPlaying() {
		return isPlaying;
	}

	public void setIsPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (isPlaying && animationName != null) {
			long time = System.nanoTime() - startTime;
			time %= cycleTimeInNanoSec;
			List<Integer> indexArray = animations.get(animationName);
			long frameTime = cycleTimeInNanoSec / indexArray.size();
			int frameIndex = (int) (time / frameTime);
			if (frameIndex == indexArray.size())
				frameIndex--;
			this.setImage(frames.get(indexArray.get(frameIndex)));
		}
	}

}
