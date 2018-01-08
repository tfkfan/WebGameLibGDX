package com.webgame.game.world.skills.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
	}
}
