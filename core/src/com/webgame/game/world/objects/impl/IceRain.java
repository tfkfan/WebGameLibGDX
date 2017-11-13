package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.objects.FallingAOESkill;

public class IceRain extends FallingAOESkill<IceRainObject> {

	public IceRain(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
		super(batch, spriteTexture, numFrames);
	}

	@Override
	public IceRainObject createObject() {
		IceRainObject obj = new IceRainObject();
		obj.setFalling(true);
		// obj.rotate(-150);
		return obj;
	}
}
