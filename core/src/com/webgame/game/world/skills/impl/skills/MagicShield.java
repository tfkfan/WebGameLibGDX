package com.webgame.game.world.skills.impl.skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.StaticAOESkill;
import com.webgame.game.world.skills.impl.skill_sprites.MagicShieldSprite;
import com.webgame.game.world.skills.impl.skill_sprites.TeleportSprite;

public class MagicShield extends StaticAOESkill<MagicShieldSprite> {
	public MagicShield(SpriteBatch batch, Texture spriteTexture) throws Exception {
		super(batch, spriteTexture, 1);
		setTimed(false);
		this.setDamage(70d);
	}

	@Override
	public MagicShieldSprite createObject() {
		MagicShieldSprite obj = new MagicShieldSprite();
		obj.setStatic(true);
		obj.setAOE(true);
		return obj;
	}

	@Override
	protected void afterCustomAnimation() {
		MagicShieldSprite obj = getSkillObjects().get(0);
		if (obj.getAnimateTimer() > obj.getAnimationMaxDuration())
			isActive = false;
	}
}
