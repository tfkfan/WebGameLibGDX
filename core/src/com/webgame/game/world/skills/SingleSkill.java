package com.webgame.game.world.skills;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class SingleSkill<T extends SkillObject> extends Skill<T> {
	public SingleSkill(SpriteBatch batch, Texture spriteTexture) throws Exception {
		super(batch, spriteTexture, 1);
		setAOE(false);

	}

	@Override
	protected void initFrame(T frame, Vector2 playerPosition, Vector2 targetPosition) {
		Vector2 vel = new Vector2(targetPosition.x - playerPosition.x, targetPosition.y - playerPosition.y);
		float len = vel.len();
		vel.set(absVelocity * vel.x / len, absVelocity * vel.y / len);

		frame.setVelocity(vel);
		frame.setFinalAnimated(false);
		frame.setActive(true);
		frame.setPosition(playerPosition.x, playerPosition.y);
	}
	
	
	@Override
	protected void updateFrame(T frame, float dt) {
		super.updateFrame(frame, dt);
		
		if(frame.isMarked()){
			if (!frame.isFinalAnimated()) {
				frame.setStatic(true);
			}
			if (frame.isFinalAnimated() && frame.isStatic()) {
				frame.setFinalAnimated(false);
				frame.setStatic(false);
				//initPositions(frame);
			}
		}
		
		if (!frame.isFinalAnimated() && frame.isStatic()) {
			frame.animateTimer += dt;
			if (frame.animateTimer > frame.animationMaxDuration) {
				frame.animateTimer = 0;
				resetSkill();
			}
		}
	}
}
