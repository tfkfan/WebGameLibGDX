package com.webgame.game.world.skills.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.StaticAOESkill;

public class Tornado extends StaticAOESkill<TornadoSprite> {
	public Tornado(SpriteBatch batch, Texture spriteTexture) throws Exception {
		super(batch, spriteTexture, 1);
		setTimed(true);
	}

	@Override
	public TornadoSprite createObject() {
		TornadoSprite obj = new TornadoSprite();
		obj.setStatic(true);
		obj.setAOE(true);
		// obj.rotate(-150);
		return obj;
	}

	@Override
	protected void afterCustomAnimation() {
	}
}
