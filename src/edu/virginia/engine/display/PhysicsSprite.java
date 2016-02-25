package edu.virginia.engine.display;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSprite extends AnimatedSprite {

	private boolean respondToGravity;
	private int gravity;

	public PhysicsSprite(String id, List<String> fileNames) {
		super(id, fileNames);
		respondToGravity = false;
		gravity = 1;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public void setRespondToGravity(boolean respondToGravity) {
		this.respondToGravity = respondToGravity;
	}

	public boolean getRespondToGravity() {
		return respondToGravity;
	}

	@Override
	protected void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (respondToGravity) {
			yPosition += gravity;
		}
		boolean reverse = false;
		for (DisplayObject object : collidableObjects) {
			if (this.collidesWith(object)) {
				reverse = true;
				break;
			}
		}
		if (reverse) {
			if (respondToGravity) {
				yPosition -= gravity;
			}
			if (respondToKeys) {
				for (String key : pressedKeys) {
					if (key.equals(UP_KEY)) {
						yPosition += MOVE_DELTA;
					}
					if (key.equals(DOWN_KEY)) {
						yPosition -= MOVE_DELTA;
					}
					if (key.equals(LEFT_KEY)) {
						xPosition += MOVE_DELTA;
					}
					if (key.equals(RIGHT_KEY)) {
						xPosition -= MOVE_DELTA;
					}
				}
			}
		}

	}

}