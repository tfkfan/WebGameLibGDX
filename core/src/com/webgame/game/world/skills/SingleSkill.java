package com.webgame.game.world.skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class SingleSkill<T extends SkillObject> extends Skill<T> {
	public SingleSkill(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
		super(batch, spriteTexture, numFrames);
		setAOE(false);
	}

	@Override
	protected void initFrame(T frame, Vector2 playerPosition, Vector2 targetPosition) {
		Vector2 vel = new Vector2(targetPosition.x - playerPosition.x, targetPosition.y - playerPosition.y);
		float len = vel.len();
		vel.set(absVelocity * vel.x / len, absVelocity * vel.y / len);

		frame.setVelocity(vel);
		// frame.setFinalAnimated(false);
		frame.setActive(true);
		frame.setPosition(playerPosition.x, playerPosition.y);
	}
}
