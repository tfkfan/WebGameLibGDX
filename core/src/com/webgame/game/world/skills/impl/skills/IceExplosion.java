package com.webgame.game.world.skills.impl.skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.StaticAOESkill;
import com.webgame.game.world.skills.impl.skill_sprites.ExplosionSprite;
import com.webgame.game.world.skills.impl.skill_sprites.IceExplosionSprite;

public class IceExplosion extends StaticAOESkill<IceExplosionSprite> {
	public IceExplosion(SpriteBatch batch, Texture spriteTexture) throws Exception {
		super(batch, spriteTexture, 1);
		setTimed(false);
		this.setDamage(70d);
	}

	@Override
	public IceExplosionSprite createObject() {
		IceExplosionSprite obj = new IceExplosionSprite();
		obj.setStatic(true);
		obj.setAOE(true);
		return obj;
	}

	@Override
	protected void afterCustomAnimation() {
		IceExplosionSprite obj = getSkillObjects().get(0);
		if (obj.getAnimateTimer() > obj.getAnimationMaxDuration())
			isActive = false;
	}
}
