package com.webgame.game.world.skills.impl.skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.StaticAOESkill;
import com.webgame.game.world.skills.impl.skill_sprites.ExplosionSprite;

public class Explosion extends StaticAOESkill<ExplosionSprite> {
	public Explosion(SpriteBatch batch, Texture spriteTexture) throws Exception {
		super(batch, spriteTexture, 1);
		setTimed(false);
		this.setDamage(50d);
	}

	@Override
	public ExplosionSprite createObject() {
		ExplosionSprite obj = new ExplosionSprite();
		obj.setStatic(true);
		obj.setAOE(true);
		return obj;
	}

	@Override
	protected void afterCustomAnimation() {
		ExplosionSprite obj = getSkillObjects().get(0);
		if (obj.getAnimateTimer() > obj.getAnimationMaxDuration())
			isActive = false;
	}
}
