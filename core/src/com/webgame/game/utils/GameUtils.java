package com.webgame.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GameUtils {
	private static int second = 1000;
	private static int minute = second*60;

	private GameUtils() {
	}

	public static Texture loadSprite(String spritePath) {
		return new Texture(Gdx.files.internal(spritePath));
	}

	public static long calcTime(int seconds, int minutes){
		return (long)(seconds*second + minutes*minute);
	}

	public static float calcCurrentHealth(Integer HP, Integer maxHP){
		return 100 - (float)(maxHP - HP)/100;
	}
}
