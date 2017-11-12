package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.objects.StaticAOESkill;

public class Tornado extends StaticAOESkill<TornadoObject> {

	public Tornado(SpriteBatch batch, String spritePath) {
		super(batch, spritePath);
		createSkill(1);
	}

	@Override
	public TornadoObject createObject() {
		TornadoObject obj = new TornadoObject();
		obj.setStatic(true);
		obj.setAOE(true);
		// obj.rotate(-150);
		return obj;
	}

}
