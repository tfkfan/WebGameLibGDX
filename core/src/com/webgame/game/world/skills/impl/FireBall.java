package com.webgame.game.world.skills.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.SingleSkill;

public class FireBall extends SingleSkill<FireBallSprite> {

	boolean isRotated;

	public FireBall(SpriteBatch batch, Texture spriteTexture) throws Exception {
		super(batch, spriteTexture);
		setTimed(false);
	}

	@Override
	public FireBallSprite createObject() {
		FireBallSprite obj = new FireBallSprite();
		return obj;
	}
}
