package com.webgame.game.world.skills.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.FallingAOESkill;

public class MeteorRain extends FallingAOESkill<MeteorRainObject> {
	public MeteorRain(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
		super(batch, spriteTexture, numFrames);
	}

	@Override
	public MeteorRainObject createObject() {
		MeteorRainObject obj = new MeteorRainObject();
		obj.setFalling(true);
		// obj.rotate(-150);
		return obj;
	}

}
