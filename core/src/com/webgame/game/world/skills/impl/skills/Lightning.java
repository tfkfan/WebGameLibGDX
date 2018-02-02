package com.webgame.game.world.skills.impl.skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.world.skills.StaticSkill;
import com.webgame.game.world.skills.impl.skill_sprites.LightningSprite;

public class Lightning extends StaticSkill<LightningSprite> {
	public Lightning(SpriteBatch batch, Texture spriteTexture) throws Exception {
		super(batch, spriteTexture, 1);
		setTimed(false);
		setAOE(false);
	}

	@Override
	public LightningSprite createObject() {
		LightningSprite obj = new LightningSprite();
		obj.setStatic(true);
		obj.setAOE(true);
		// obj.rotate(-150);
		return obj;
	}

	@Override
	protected void initPositions(LightningSprite frame, Vector2 target) {
		float x = target.x - frame.getWidth()/2;
		float y = target.y;
		frame.updateDistance();
		frame.setAnimateTimer(0);
		frame.setActive(true);
		frame.setPosition(x, y);
	}
}
