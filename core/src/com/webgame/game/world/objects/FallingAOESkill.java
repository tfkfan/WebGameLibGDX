package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class FallingAOESkill<T extends SkillObject> extends Skill<T> {

	protected float fallTimer;
	protected final float fallDuration = 10;
	protected int index;

	public FallingAOESkill(SpriteBatch batch, String spritePath) {
		super(batch, spritePath);
		fallTimer = 0;
		index = 0;
	}

	@Override
	public void updateTimers() {
		super.updateTimers();
		fallTimer = 0;
	}

	@Override
	public void customAnimation(float dt) {
		if (skillTimer >= fallDuration) {
			isActive = false;
			for (int i = 0; i < skillObjectsNum; i++) {
				SkillObject obj = skillObjects.get(i);
				obj.setActive(false);
				obj.setStatic(false);
				obj.setPosition(0, 0);
				obj.setFinalAnimated(false);
				
			}
			index = 0;
			updateTimers();
			return;	
		}

		skillTimer += dt;
		float tmp = 0.1f;

		if (index != -1) {
			fallTimer += dt;
			if (fallTimer >= tmp) {
				SkillObject obj = skillObjects.get(index);

				obj.updateDistance();
				obj.setActive(true);
		
				if (index < skillObjectsNum - 1)
					index++;
				else
					index = -1;
				fallTimer = 0;
			}
		}

		if (fallTimer >= tmp)
			fallTimer = 0;

		for (int i = 0; i < skillObjectsNum; i++) {
			SkillObject obj = skillObjects.get(i);
			if (obj.isActive()) {
				obj.move(dt);
				obj.draw(batch);
			}
		}

	}

}
