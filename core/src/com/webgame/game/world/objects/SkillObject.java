package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class SkillObject extends GameObject {
	protected boolean isActive;

	public SkillObject() {

	}

	public void setSkillActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}

	@Override
	public void move(float dt) {
		setPosition(getX() + getVelocity().x - xOffset, getY() + getVelocity().y - yOffset);
		super.move(dt);
	}

	public abstract void initSkill(SpriteBatch batch, String spritePath);
}
