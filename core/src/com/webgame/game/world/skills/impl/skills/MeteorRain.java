package com.webgame.game.world.skills.impl.skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.FallingAOESkill;
import com.webgame.game.world.skills.impl.skill_sprites.MeteorRainSprite;

public class MeteorRain extends FallingAOESkill<MeteorRainSprite> {
	public MeteorRain(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
		super(batch, spriteTexture, numFrames);
	}

	@Override
	public MeteorRainSprite createObject() {
		MeteorRainSprite obj = new MeteorRainSprite();
		obj.setFalling(true);
		// obj.rotate(-150);
		return obj;
	}

}
