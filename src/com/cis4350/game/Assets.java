package com.cis4350.game;

import com.cis4350.framework.Image;
import com.cis4350.framework.Music;
import com.cis4350.framework.Sound;

public class Assets {

	public static Image menu, splash, background, background2, fBird, upPipe,
			downPipe;
	public static Sound click;
	public static Music theme;

	public static void load(SampleGame sampleGame) {
		theme = sampleGame.getAudio().createMusic("menutheme.mp3");
		theme.setLooping(true);
		theme.setVolume(0.85f);
		theme.play();
	}
}