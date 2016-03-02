package edu.virginia.lab6test;

import java.util.ArrayList;
import java.util.Arrays;

import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.CollideEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.tween.Tween;
import edu.virginia.engine.tween.TweenEvent;
import edu.virginia.engine.tween.TweenJuggler;
import edu.virginia.engine.tween.TweenableParam;
import edu.virginia.engine.tween.ZigZagTweenTransition;

public class LabSixGame extends Game implements IEventListener {
	
	private static final String MARIO_ID = "mario";
	private static final String PLATFORM_ID = "platform";
	private static final String COIN_ID = "coin";
	
	private PhysicsSprite mario;
	private Sprite platform1;
	private Sprite platform2;
	private Sprite coin;
	private Tween coinMove;
	private boolean coinPresent;
	private boolean coinMoved;
	
	public LabSixGame() {
		super("Lab six test game", 800, 800);
		setUpSprites();
		coinPresent = true;
		coinMoved = false;
	}
	
	private void setUpSprites() {
		mario = new PhysicsSprite(MARIO_ID, new ArrayList<String>(Arrays.asList("animation_1.png", "animation_2.png",
				"animation_3.png", "animation_4.png", "animation_5.png", "animation_6.png")));
		mario.setxPosition(200);
		mario.setyPosition(600);
		mario.addAnimationSet("walk", Arrays.asList(0, 1, 2, 3));
		mario.addAnimationSet("jump", Arrays.asList(0, 4, 5));
		// set animation name and time
		mario.setAnimation("walk");
		mario.setRespondToKeys(true);
		mario.setCycleTimeInNanoSec(1000000000);
		mario.addEventListener(this, CollideEvent.COLLIDE_START);
		mario.setRespondToGravity(true);
		this.addChild(mario);
		platform1 = new Sprite(PLATFORM_ID, "brickblock.png");
		platform1.setxPosition(100);
		platform1.setyPosition(200);
		mario.addCollidableObject(platform1);
		platform1.addCollidableObject(mario);
		platform1.addEventListener(this, CollideEvent.COLLIDE_START);
		this.addChild(platform1);
		Tween marioAppear = new Tween(mario);
		marioAppear.animate(TweenableParam.ALPHA, 0, 1, 2000);
		marioAppear.animate(TweenableParam.SCALE_X, 0, 1, 2000);
		marioAppear.animate(TweenableParam.SCALE_Y, 0, 1, 2000);
		TweenJuggler.getInstance().addTween(marioAppear);
		// Add bedrock
		platform2 = new Sprite(PLATFORM_ID, "brickblock.png");
		platform2.setxPosition(200);
		platform2.setyPosition(700);
		mario.addCollidableObject(platform2);
		platform2.addCollidableObject(mario);
		platform2.addEventListener(this, CollideEvent.COLLIDE_START);
		this.addChild(platform2);
		// Add coins
		coin = new Sprite(COIN_ID, "coin.png");
		coin.setxPosition(10);
		coin.setyPosition(10);
		coin.addCollidableObject(mario);
		coin.addEventListener(this, CollideEvent.COLLIDE_START);
		this.addChild(coin);
		Tween coinAppear = new Tween(coin);
		coinAppear.animate(TweenableParam.ALPHA, 0, 1, 2000);
		coinAppear.animate(TweenableParam.SCALE_X, 0, 1, 2000);
		coinAppear.animate(TweenableParam.SCALE_Y, 0, 1, 2000);
		TweenJuggler.getInstance().addTween(coinAppear);
	}

	@Override
	public void handleEvent(Event event) {
		if (event.getSource() == coin && !coinMoved) {
			coinMove = new Tween(coin, new ZigZagTweenTransition());
			coinMove.animate(TweenableParam.X, coin.getxPosition(), 400, 3000);
			coinMove.animate(TweenableParam.SCALE_X, coin.getScaleX(), 2, 3000);
			coinMove.animate(TweenableParam.Y, coin.getyPosition(), 400, 3000);
			coinMove.animate(TweenableParam.SCALE_Y, coin.getScaleY(), 2, 3000);
			coinMove.addEventListener(this, TweenEvent.TWEEN_COMPLETE_EVENT);
			TweenJuggler.getInstance().addTween(coinMove);
			coinMoved = true;
		}
		if(event.getSource() == coinMove && coinPresent) {
			Tween disappearTween = new Tween(coin);
			disappearTween.animate(TweenableParam.ALPHA, coin.getAlpha(), 0, 2000);
			disappearTween.animate(TweenableParam.SCALE_X, coin.getScaleX(), 0, 2000);
			disappearTween.animate(TweenableParam.SCALE_Y, coin.getScaleY(), 0, 2000);
			TweenJuggler.getInstance().addTween(disappearTween);
			coinPresent = false;
		}
		
	}
	
	public static void main(String[] args) {
		LabSixGame game = new LabSixGame();
		game.start();
	}

}
