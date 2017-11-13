package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.objects.StaticAOESkill;

public class Tornado extends StaticAOESkill<TornadoObject> {
	public Tornado(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
		super(batch, spriteTexture, numFrames);
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

	@Override
	protected void initFrame(TornadoObject frame) {
		// TODO Auto-generated method stub
		
	}

}
