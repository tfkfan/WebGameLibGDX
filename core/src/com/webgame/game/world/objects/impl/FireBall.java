package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.objects.Skill;

public class FireBall extends Skill<FireBallObject> {

	public FireBall(SpriteBatch batch, String spritePath) {
		super(batch, spritePath);
		createSkill(1);
	}

	@Override
	public FireBallObject createObject() {
		return new FireBallObject();
	}

	@Override
	public FireBallObject[] createObjectsArray(Integer num) {
		return new FireBallObject[num];
	}

	@Override
	public void customAnimation(float dt) {
		// TODO Auto-generated method stub
		
	}

	
}
