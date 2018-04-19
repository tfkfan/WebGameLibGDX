package com.webgame.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GameUtils {
	private GameUtils() {
	}

	public static Texture loadSprite(String spritePath) {
		return new Texture(Gdx.files.internal(spritePath));
	}


	public static float calcHealthLineWidth(float width, Integer HP, Integer maxHP){
		return width*((float)HP/maxHP);
	}
}
