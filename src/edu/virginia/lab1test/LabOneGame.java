package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class LabOneGame extends Game{

	/* Create a sprite object for our game. We'll use mario */
	Sprite mario = new Sprite("Mario", "Mario.png");
	Sprite mario2 = new Sprite("Mario2", "Mario.png");
	AnimatedSprite movingMario = new AnimatedSprite("AnimatedMario", new ArrayList<String>(Arrays.asList("animation_1.png",
				"animation_2.png", "animation_3.png", "animation_4.png", "animation_5.png", "animation_6.png")));
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public LabOneGame() {
		super("Lab One Test Game", 500, 300);
		movingMario.addAnimationSet("walk", Arrays.asList(0, 1, 2, 3));
		movingMario.addAnimationSet("jump", Arrays.asList(0, 4, 5));
		// set animation name and time
		movingMario.setAnimation("walk");
		movingMario.setCycleTimeInNanoSec(1000000000);
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys);
		
		/* Make sure mario is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
		if(mario != null) mario.update(pressedKeys);
		if(mario2 != null) mario2.update(pressedKeys);
		if(movingMario != null) movingMario.update(pressedKeys);
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
	 * the screen, we need to make sure to override this method and call mario's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		
		/* Same, just check for null in case a frame gets thrown in before Mario is initialized */
		if(mario != null) {
			mario.setxPosition(50);
			mario.setyPosition(25);
			mario.setPivotPoint(new Point(mario.getUnscaledWidth(), mario.getUnscaledHeight() / 2));
			mario.setRotation(mario.getRotation() + 0.001);
			mario.setScaleX(2);
			mario.draw(g);
		}
		
		if(mario2 != null) {
			//mario2.setVisible(false);
			mario2.setxPosition(200);
			mario2.setyPosition(50);
			mario2.setAlpha(0.5f);
			mario2.setPivotPoint(new Point(mario.getUnscaledWidth() / 2, mario.getUnscaledHeight() / 4));
			mario2.setRotation(Math.PI / 2);
			mario2.setScaleY(0.5f);
			mario2.draw(g);
		}
		
		if(movingMario != null) {
			movingMario.setxPosition(200);
			movingMario.setyPosition(100);
			movingMario.draw(g);
		}
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		LabOneGame game = new LabOneGame();
		game.start();

	}
}
