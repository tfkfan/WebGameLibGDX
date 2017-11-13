package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.objects.FallingAOESkill;

public class FireRain extends FallingAOESkill<FireRainObject> {
	public FireRain(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
		super(batch, spriteTexture, numFrames);
	}

	@Override
	public FireRainObject createObject() {
		FireRainObject obj = new FireRainObject();
		obj.setFalling(true);
		// obj.rotate(-150);
		return obj;
	}

}
