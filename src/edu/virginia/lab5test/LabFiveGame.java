package edu.virginia.lab5test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.CollideEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.util.SoundManager;

public class LabFiveGame extends Game implements IEventListener {

	private static final String IMPACT_ID = "impact";
	private static final String SCORE_ID = "score";
	private static final String MARIO_ID = "mario";
	private static final String PLATFORM_ID = "platform";
	private static final String COIN_ID = "coin";
	private static final String BGM_ID = "bgm";

	private PhysicsSprite mario;
	private Sprite platform1;
	private Sprite platform2;
	private Sprite coin;
	private SoundManager soundManager;

	public LabFiveGame() {
		super("Lab five test game", 800, 800);
		setUpSprites();
		try {
			soundManager = new SoundManager();
			soundManager.loadSoundEffect(IMPACT_ID, "resources/sm64_impact.wav");
			soundManager.loadSoundEffect(SCORE_ID, "resources/sm64_key_get.wav");
			soundManager.loadMusic(BGM_ID, "resources/smb_warning.wav");
			soundManager.playMusic(BGM_ID);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		// Add platforms
		platform1 = new Sprite(PLATFORM_ID, "brickblock.png");
		platform1.setxPosition(100);
		platform1.setyPosition(200);
		mario.addCollidableObject(platform1);
		platform1.addCollidableObject(mario);
		platform1.addEventListener(this, CollideEvent.COLLIDE_START);
		this.addChild(platform1);
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
	}

	@Override
	public void handleEvent(Event event) {
		DisplayObject source = (DisplayObject) event.getSource();
		if (source == coin) {
			Random rnd = new Random(System.currentTimeMillis());
			coin.setxPosition(rnd.nextInt(800));
			coin.setxPosition(rnd.nextInt(800));
		} else if(source.getId().equals(PLATFORM_ID)) {
			System.out.println("Colliding with platform!");
		}

	}

	public static void main(String[] args) {
		LabFiveGame game = new LabFiveGame();
		game.start();
	}

}
