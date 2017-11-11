package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class SingleSkill<T extends SkillObject> extends Skill<T> {

	public SingleSkill(SpriteBatch batch, String spritePath) {
		super(batch, spritePath);
	}

	@Override
	public void customAnimation(float dt) {
		for (int i = 0; i < skillObjectsNum; i++) {
			SkillObject obj = skillObjects.get(i);
			if (obj.isActive()) {
				obj.move(dt);
				obj.draw(batch);
			}
		}

	}

}
