package com.webgame.game.world.skills.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.FallingAOESkill;

public class StoneRain extends FallingAOESkill<StoneRainSprite> {
	public StoneRain(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
		super(batch, spriteTexture, numFrames);
	}

	@Override
	public StoneRainSprite createObject() {
		StoneRainSprite obj = new StoneRainSprite();
		obj.setFalling(true);
		// obj.rotate(-150);
		return obj;
	}

}
