package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class SkillObject extends GameObject {
	protected boolean isActive;
	protected boolean isStatic;
	protected boolean isFalling;

	public SkillObject() {
		super();
	}

	public void setSkillActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}
	
	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	
	public boolean isFalling() {
		return isFalling;
	}

	public void setFalling(boolean isFalling) {
		this.isFalling = isFalling;
	}

	@Override
	public void move(float dt) {
		setPosition(getX() + getVelocity().x - xOffset, getY() + getVelocity().y - yOffset);
		super.move(dt);
	}

	public abstract void initSkill(SpriteBatch batch, String spritePath);
}
