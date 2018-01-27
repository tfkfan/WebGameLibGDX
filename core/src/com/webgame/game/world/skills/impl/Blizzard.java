package com.webgame.game.world.skills.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.FallingAOESkill;

public class Blizzard extends FallingAOESkill<BlizzardSprite> {

	public Blizzard(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
		super(batch, spriteTexture, numFrames);
		setTimed(true);
	}

	@Override
	public BlizzardSprite createObject() {
		BlizzardSprite obj = new BlizzardSprite();
		obj.setFalling(true);
		return obj;
	}
}
