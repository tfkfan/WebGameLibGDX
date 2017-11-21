package com.webgame.game.world.skills.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.SingleSkill;

public class FireBall extends SingleSkill<FireBallObject> {

	boolean isRotated;

	public FireBall(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
		super(batch, spriteTexture, numFrames);
		setTimed(false);
	}

	@Override
	public FireBallObject createObject() {
		FireBallObject obj = new FireBallObject();
		return obj;
	}

	@Override
	protected void afterCustomAnimation() {
		// TODO Auto-generated method stub
		
	}



}
