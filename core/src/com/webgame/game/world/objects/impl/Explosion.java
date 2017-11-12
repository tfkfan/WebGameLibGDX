package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.objects.StaticAOESkill;

public class Explosion extends StaticAOESkill<ExplosionObject> {

	public Explosion(SpriteBatch batch, String spritePath) {
		super(batch, spritePath);
		createSkill(1);
	}

	@Override
	public ExplosionObject createObject() {
		ExplosionObject obj = new ExplosionObject();
		obj.setStatic(true);
		obj.setAOE(true);
		// obj.rotate(-150);
		return obj;
	}

}
