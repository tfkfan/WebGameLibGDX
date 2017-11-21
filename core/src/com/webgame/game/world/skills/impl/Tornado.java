package com.webgame.game.world.skills.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.StaticAOESkill;

public class Tornado extends StaticAOESkill<TornadoObject> {
	public Tornado(SpriteBatch batch, Texture spriteTexture) throws Exception {
		super(batch, spriteTexture, 1);
		setTimed(true);
	}

	@Override
	public TornadoObject createObject() {
		TornadoObject obj = new TornadoObject();
		obj.setStatic(true);
		obj.setAOE(true);
		// obj.rotate(-150);
		return obj;
	}

	@Override
	protected void afterCustomAnimation() {
		// TODO Auto-generated method stub
		
	}
}
