package edu.virginia.lab4test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;

public class LabFourGame extends Game {

	private AnimatedSprite mario;
	private Sprite coin;
	private QuestManager questManager;

	private void setUpSprites() {
		mario = new AnimatedSprite("AnimatedMario", new ArrayList<String>(Arrays.asList("animation_1.png",
				"animation_2.png", "animation_3.png", "animation_4.png", "animation_5.png", "animation_6.png")));
		mario.setxPosition(200);
		mario.setyPosition(200);
		mario.addAnimationSet("walk", Arrays.asList(0, 1, 2, 3));
		mario.addAnimationSet("jump", Arrays.asList(0, 4, 5));
		// set animation name and time
		mario.setAnimation("walk");
		mario.setRespondToKeys(true);
		mario.setCycleTimeInNanoSec(1000000000);
		coin = new Sprite("Coin", "coin.png");
		coin.setScaleX(0.3f);
		coin.setScaleY(0.3f);
		Random rnd = new Random(System.currentTimeMillis());
		coin.setxPosition(rnd.nextInt(800));
		coin.setyPosition(rnd.nextInt(800));
		this.addChild(mario);
		this.addChild(coin);
	}

	public LabFourGame() {
		super("Lab four test game", 800, 800);
		setUpSprites();
		questManager = new QuestManager(mario, coin);
		coin.addEventListener(questManager, PickedUpEvent.COIN_PICKED_UP);
	}

	public static void main(String[] args) {
		LabFourGame game = new LabFourGame();
		game.start();
	}

	@Override
	protected void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if(coin != null && mario != null && isClose()) {
			coin.dispatchEvent(new PickedUpEvent(PickedUpEvent.COIN_PICKED_UP, coin));
		}
	}

	private boolean isClose() {
		return (Math.pow(mario.getxPosition() - coin.getxPosition(), 2)
				+ Math.pow(mario.getyPosition() - coin.getyPosition(), 2)) < 200;
	}

}
