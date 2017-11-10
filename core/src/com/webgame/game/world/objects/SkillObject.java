package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class SkillObject extends GameObject {
	protected boolean isActive;

	public SkillObject() {

	}

	public void setSkillActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public abstract void initSkill(SpriteBatch batch, String spritePath);
}
