package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.objects.SingleSkill;

public class FireBall extends SingleSkill<FireBallObject> {

	boolean isRotated;

	public FireBall(SpriteBatch batch, String spritePath) {
		super(batch, spritePath);
		createSkill(1);
	}

	@Override
	public FireBallObject createObject() {
		FireBallObject obj = new FireBallObject();
		return obj;
	}

	@Override
	public void afterCast() {
		FireBallObject obj = (FireBallObject) this.getSkillObjects().get(0);
		obj.setActive(true);
	}

	@Override
	public void afterAnimation() {
		FireBallObject obj = (FireBallObject) this.getSkillObjects().get(0);
		if (!obj.isActive())
			setActive(false);
	}

}
