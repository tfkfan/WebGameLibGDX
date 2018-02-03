package com.webgame.game.world.skills.impl.skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.StaticAOESkill;
import com.webgame.game.world.skills.impl.skill_sprites.MagicBuffSprite;
import com.webgame.game.world.skills.impl.skill_sprites.TeleportSprite;

public class Teleport extends StaticAOESkill<TeleportSprite> {
	public Teleport(SpriteBatch batch, Texture spriteTexture) throws Exception {
		super(batch, spriteTexture, 1);
		setTimed(false);
		this.setDamage(70d);
	}

	@Override
	public TeleportSprite createObject() {
		TeleportSprite obj = new TeleportSprite();
		obj.setStatic(true);
		obj.setAOE(true);
		return obj;
	}
}
