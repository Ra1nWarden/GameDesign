package edu.virginia.engine.display;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import edu.virginia.engine.events.CollideEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventDispatcher;
import edu.virginia.engine.events.IEventListener;

/**
 * A very basic display object for a java based gaming engine
 * 
 */
public class DisplayObject implements IEventDispatcher {

	protected static final String UP_KEY = "Up";
	protected static final String DOWN_KEY = "Down";
	protected static final String LEFT_KEY = "Left";
	protected static final String RIGHT_KEY = "Right";
	protected static final int MOVE_DELTA = 3;

	/* All DisplayObject have a unique id */
	protected String id;

	/* The image that is displayed by this object */
	protected BufferedImage displayImage;
	
	protected DisplayObject parent;

	protected boolean visible = true;

	protected int xPosition;
	protected int yPosition;

	protected Point pivotPoint = new Point(0, 0);

	protected float scaleX = 1.0f;
	protected float scaleY = 1.0f;
	protected double rotation;
	protected float alpha = 1.0f;
	protected float prevAlpha;

	protected boolean respondToKeys = false;
	protected List<DisplayObject> collidableObjects = new ArrayList<DisplayObject>();
	
	protected HashMap<String, List<IEventListener> > eventListeners = new HashMap<String, List<IEventListener> >();

	public void setRespondToKeys(boolean flag) {
		respondToKeys = flag;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public Point getPivotPoint() {
		return pivotPoint;
	}

	public void setPivotPoint(Point pivotPoint) {
		this.pivotPoint = pivotPoint;
	}

	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public void setDisplayImage(BufferedImage displayImage) {
		this.displayImage = displayImage;
	}

	public DisplayObject getParent() {
		return parent;
	}

	public void setParent(DisplayObject parent) {
		this.parent = parent;
	}

	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this.setId(id);
	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	/**
	 * Returns the unscaled width and height of this display object
	 */
	public int getUnscaledWidth() {
		if (displayImage == null)
			return 0;
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if (displayImage == null)
			return 0;
		return displayImage.getHeight();
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
	}

	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if (image == null)
			return;
		displayImage = image;
	}
	
	public Rectangle getHitbox() {
		return getHitboxForParent(parent);
	}
	
	public Rectangle getHitboxForParent(DisplayObject target) {
		if(target == parent) {
			return new Rectangle(xPosition, yPosition, getUnscaledWidth(), getUnscaledHeight());
		} else {
			if(parent == null) {
				throw new IllegalArgumentException();
			}
			Rectangle prev = parent.getHitboxForParent(target);
			prev.x += xPosition;
			prev.y += yPosition;
			prev.width = getUnscaledWidth();
			prev.height = getUnscaledHeight();
			return prev;
		}
	}
	
	public boolean collidesWith(DisplayObject obj) {
		Rectangle globalRectangle1 = this.getHitboxForParent(null);
		Rectangle globalRectangle2 = obj.getHitboxForParent(null);
		return globalRectangle1.intersects(globalRectangle2);
	}
	
	public void addCollidableObject(DisplayObject obj) {
		collidableObjects.add(obj);
	}

	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 */
	protected void update(ArrayList<String> pressedKeys) {
		if (respondToKeys) {
			for (String key : pressedKeys) {
				if (key.equals(UP_KEY)) {
					yPosition -= MOVE_DELTA;
				}
				if (key.equals(DOWN_KEY)) {
					yPosition += MOVE_DELTA;
				}
				if (key.equals(LEFT_KEY)) {
					xPosition -= MOVE_DELTA;
				}
				if (key.equals(RIGHT_KEY)) {
					xPosition += MOVE_DELTA;
				}
			}
		}
		for(DisplayObject object : collidableObjects) {
			if(this.collidesWith(object)) {
				this.dispatchEvent(new CollideEvent(CollideEvent.COLLIDE_START, this));
			}
		}
	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 */
	public void draw(Graphics g) {

		if (displayImage != null && visible) {

			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);

			/*
			 * Actually draw the image, perform the pivot point translation here
			 */
			g2d.drawImage(displayImage, 0, 0, (int) (getUnscaledWidth()), (int) (getUnscaledHeight()), null);

			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);
		}
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 */
	protected void applyTransformations(Graphics2D g2d) {
		g2d.translate(xPosition, yPosition);
		g2d.rotate(rotation, pivotPoint.getX(), pivotPoint.getY());
		g2d.scale(scaleX, scaleY);
		AlphaComposite alcom = (AlphaComposite) g2d.getComposite();
		prevAlpha = alcom.getAlpha();
		AlphaComposite newAlcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, prevAlpha * alpha);
		g2d.setComposite(newAlcom);
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 */
	protected void reverseTransformations(Graphics2D g2d) {
		AlphaComposite newAlcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, prevAlpha);
		g2d.setComposite(newAlcom);
		g2d.scale(1 / scaleX, 1 / scaleY);
		g2d.rotate(-rotation, pivotPoint.getX(), pivotPoint.getY());
		g2d.translate(-xPosition, -yPosition);
	}

	@Override
	public void addEventListener(IEventListener listener, String eventType) {
		if(eventListeners.containsKey(eventType)) {
			eventListeners.get(eventType).add(listener);
		} else {
			List<IEventListener> listeners = new ArrayList<IEventListener>();
			listeners.add(listener);
			eventListeners.put(eventType, listeners);
		}
		
	}

	@Override
	public void removeEventListener(IEventListener listener, String eventType) {
		if(eventListeners.containsKey(eventType)) {
			eventListeners.get(eventType).remove(listener);
		}
	}

	@Override
	public void dispatchEvent(Event event) {
		String eventType = event.getEventType();
		if(eventListeners.containsKey(eventType)) {
			for(IEventListener each : eventListeners.get(eventType)) {
				each.handleEvent(event);
			}
		}
	}

	@Override
	public boolean hasEventListener(IEventListener listener, String eventType) {
		if(eventListeners.containsKey(eventType)) {
			return eventListeners.get(eventType).contains(listener);
		} else {
			return false;
		}
	}

}