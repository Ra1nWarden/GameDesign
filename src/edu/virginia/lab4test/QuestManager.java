package edu.virginia.lab4test;

import java.util.Random;

import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;

public class QuestManager implements IEventListener {
	
	private Sprite mario;
	private Sprite coin;
	
	public QuestManager(Sprite mario, Sprite coin) {
		this.mario = mario;
		this.coin = coin;
	}

	@Override
	public void handleEvent(Event event) {
		if(event.getEventType().equals(PickedUpEvent.COIN_PICKED_UP) && event.getSource() == coin) {
			System.out.println("Picked up coin!");
			Random rnd = new Random(System.currentTimeMillis());
			coin.setxPosition(rnd.nextInt(800));
			coin.setyPosition(rnd.nextInt(800));
		}		
	}

}
