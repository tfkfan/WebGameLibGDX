package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class StaticAOESkill<T extends SkillObject> extends Skill<T> {

	protected float fallTimer;
	protected final float fallDuration = 10;
	protected int index;

	public StaticAOESkill(SpriteBatch batch, String spritePath) {
		super(batch, spritePath);
		createSkill(10);
		fallTimer = 0;
		index = 0;
	}

	@Override
	public void updateTimers() {
		super.updateTimers();
		fallTimer = 0;
		skillTimer = 0;
	}

	@Override
	public void customAnimation(float dt) {
		if (!isActive)
			return;



	}

}
