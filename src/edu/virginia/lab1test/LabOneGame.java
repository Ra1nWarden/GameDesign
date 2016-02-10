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
 * Example game that utilizes our engine. We can create a simple prototype game
 * with just a couple lines of code although, for now, it won't be a very fun
 * game :)
 */
public class LabOneGame extends Game {

	/* Create a sprite object for our game. We'll use mario */
	Sprite mario = new Sprite("Mario", "Mario.png");
	Sprite mario2 = new Sprite("Mario2", "Mario.png");
	AnimatedSprite movingMario = new AnimatedSprite("AnimatedMario",
			new ArrayList<String>(Arrays.asList("animation_1.png", "animation_2.png", "animation_3.png",
					"animation_4.png", "animation_5.png", "animation_6.png")));

	/**
	 * Constructor. See constructor in Game.java for details on the parameters
	 * given
	 */
	public LabOneGame() {
		super("Lab One Test Game", 800, 800);
		mario.setxPosition(100);
		mario.setyPosition(100);
		mario.setPivotPoint(new Point(mario.getUnscaledWidth(), mario.getUnscaledHeight() / 2));
		mario.setScaleX(2);
		// attach health bar
		Sprite healthBar = new Sprite("MarioHealth", "health_bar.png");
		// scale it to be the same width as mario
		healthBar.setScaleX((float) mario.getUnscaledWidth() / healthBar.getUnscaledWidth());
		healthBar.setyPosition(-healthBar.getUnscaledHeight());
		mario.addChild(healthBar);
		mario2.setxPosition(400);
		mario2.setyPosition(400);
		mario2.setAlpha(0.2f);
		mario2.setPivotPoint(new Point(mario.getUnscaledWidth() / 2, mario.getUnscaledHeight() / 4));
		mario2.setRotation(Math.PI / 2);
		mario2.setScaleY(0.5f);
		Sprite healthBar2 = new Sprite("Mario2Health", "health_bar.png");
		healthBar2.setyPosition(-healthBar2.getUnscaledHeight());
		healthBar2.setScaleX((float) mario2.getUnscaledWidth() / healthBar2.getUnscaledWidth());
		mario2.addChild(healthBar2);
		movingMario.setxPosition(600);
		movingMario.setyPosition(600);
		movingMario.addAnimationSet("walk", Arrays.asList(0, 1, 2, 3));
		movingMario.addAnimationSet("jump", Arrays.asList(0, 4, 5));
		// set animation name and time
		movingMario.setAnimation("walk");
		movingMario.setCycleTimeInNanoSec(1000000000);
		this.addChild(mario);
		this.addChild(mario2);
		this.addChild(movingMario);
	}

	/**
	 * Engine automatically invokes draw() every frame as well. If we want to
	 * make sure mario gets drawn to the screen, we need to make sure to
	 * override this method and call mario's draw method.
	 */
	@Override
	public void draw(Graphics g) {
		if(mario != null) {
			mario.setRotation(mario.getRotation() + 0.001);
		}
		super.draw(g);
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts
	 * the timer that calls update() and draw() every frame
	 */
	public static void main(String[] args) {
		LabOneGame game = new LabOneGame();
		game.start();

	}
}
