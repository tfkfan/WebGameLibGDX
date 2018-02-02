package com.webgame.game.world.skills.impl.skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.StaticAOESkill;
import com.webgame.game.world.skills.impl.skill_sprites.IceExplosionSprite;
import com.webgame.game.world.skills.impl.skill_sprites.MagicBuffSprite;

public class MagicBuff extends StaticAOESkill<MagicBuffSprite> {
	public MagicBuff(SpriteBatch batch, Texture spriteTexture) throws Exception {
		super(batch, spriteTexture, 1);
		setTimed(false);
		this.setDamage(70d);
	}

	@Override
	public MagicBuffSprite createObject() {
		MagicBuffSprite obj = new MagicBuffSprite();
		obj.setStatic(true);
		obj.setAOE(true);
		return obj;
	}

	@Override
	protected void afterCustomAnimation() {
		MagicBuffSprite obj = getSkillObjects().get(0);
		if (obj.getAnimateTimer() > obj.getAnimationMaxDuration())
			isActive = false;
	}
}
