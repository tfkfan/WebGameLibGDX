package com.webgame.game.world.skills.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.StaticAOESkill;

public class Explosion extends StaticAOESkill<ExplosionObject> {

	public Explosion(SpriteBatch batch, Texture spriteTexture) throws Exception {
		super(batch, spriteTexture, 1);
		setTimed(false);
		this.setDamage(30d);
	}

	@Override
	public ExplosionObject createObject() {
		ExplosionObject obj = new ExplosionObject();
		obj.setStatic(true);
		obj.setAOE(true);
		// obj.rotate(-150);
		return obj;
	}

	@Override
	protected void afterCustomAnimation() {
		ExplosionObject obj = this.getSkillObjects().get(0);
		if(obj.getAnimateTimer() > obj.getAnimationMaxDuration()) {
			isActive = false;
		}
	}

}
