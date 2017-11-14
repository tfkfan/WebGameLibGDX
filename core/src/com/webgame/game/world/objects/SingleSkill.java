package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class SingleSkill<T extends SkillObject> extends Skill<T> {
	public SingleSkill(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
		super(batch, spriteTexture, numFrames);
	}

	@Override
	public void customAnimation(float dt) {
		for (int i = 0; i < numFrames; i++) {
			SkillObject obj = skillObjects.get(i);
			if (obj.isActive()) {
				obj.update(dt);
				obj.draw(batch);
			}
		}

	}
	
	@Override
	protected void initFrame(T frame, Vector2 playerPosition, Vector2 targetPosition) {
		
	}

}
