package edu.virginia.engine.util;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class SoundManager {

	private HashMap<String, String> soundMap;
	private HashMap<String, String> musicMap;

	public SoundManager() throws LineUnavailableException {
		soundMap = new HashMap<String, String>();
		musicMap = new HashMap<String, String>();
	}

	public void loadSoundEffect(String id, String fileName) {
		soundMap.put(id, fileName);
	}

	public void playSoundEffect(String id) throws Exception {
		if (soundMap.containsKey(id)) {
			try {
				Clip clip = AudioSystem.getClip();
				if(!clip.isRunning()) {
					if(clip.isOpen()) {
						clip.stop();
						clip.close();
					}
					clip.open(AudioSystem.getAudioInputStream(new File(soundMap.get(id)).getAbsoluteFile()));
					System.out.println("clip position is " + clip.getMicrosecondPosition());
					clip.start();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public void loadMusic(String id, String fileName) throws Exception {
		musicMap.put(id, fileName);
	}

	public void playMusic(String id) throws Exception {
		if (musicMap.containsKey(id)) {
			try {
				Clip clip = AudioSystem.getClip();
				if(!clip.isRunning()) {
					if(clip.isOpen()) {
						clip.close();
					}
					clip.open(AudioSystem.getAudioInputStream(new File(musicMap.get(id)).getAbsoluteFile()));
					clip.start();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
