package edu.virginia.engine.display;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class DisplayObjectContainer extends DisplayObject {

	private List<DisplayObject> children = new ArrayList<DisplayObject>();

	public DisplayObjectContainer(String id) {
		super(id);
	}

	public DisplayObjectContainer(String id, String fileName) {
		super(id, fileName);
	}

	public void clearChildren() {
		children.clear();
	}
	
	public void addChild(DisplayObject child) {
		children.add(child);
		child.setParent(this);
	}
	
	public void removeChild(DisplayObject child) {
		children.remove(child);
		child.setParent(null);
	}

	public boolean contains(DisplayObject object) {
		return children.contains(object);
	}

	public DisplayObject getChild(String id) {
		for (DisplayObject object : children) {
			if (object.getId().equals(id)) {
				return object;
			}
		}
		return null;
	}

	public List<DisplayObject> getChildren() {
		return children;
	}

	public DisplayObject getChild(int index) {
		if (index < children.size()) {
			return children.get(index);
		}
		return null;
	}

	@Override
	protected void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		for (DisplayObject child : children) {
			child.update(pressedKeys);
		}
	}

	@Override
	public void draw(Graphics g) {
		if (isVisible()) {
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);
			if (displayImage != null) {
				AlphaComposite composite = (AlphaComposite) g2d.getComposite();
				g2d.drawImage(displayImage, 0, 0, (int) (getUnscaledWidth()), (int) (getUnscaledHeight()), null);
			}
			for (DisplayObject each : children) {
				each.draw(g);
			}
			reverseTransformations(g2d);
		}
	}

}
