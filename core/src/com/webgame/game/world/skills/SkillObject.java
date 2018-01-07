package com.webgame.game.world.skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.world.objects.GameObject;

public abstract class SkillObject extends GameObject implements Cloneable {
	protected boolean isActive;
	protected boolean isStatic;
	protected boolean isFalling;
	protected boolean isFinalAnimated;
	protected boolean isAOE;
	protected boolean isMarked;
	
	protected final Vector2 distance;
	
	protected float animationDuration = 0.2f;
	protected float animationMaxDuration;
	protected float animateTimer = 0;

	public SkillObject() {
		super();
		distance = new Vector2(0, 0);
		animationMaxDuration = animationDuration * 3;
	}
	
	public float getAnimationDuration() {
		return animationDuration;
	}

	public void setAnimationDuration(float animationDuration) {
		this.animationDuration = animationDuration;
	}

	public float getAnimationMaxDuration() {
		return animationMaxDuration;
	}

	public void setAnimationMaxDuration(float animationMaxDuration) {
		this.animationMaxDuration = animationMaxDuration;
	}

	public float getAnimateTimer() {
		return animateTimer;
	}

	public void setAnimateTimer(float animateTimer) {
		this.animateTimer = animateTimer;
	}
	
	public boolean isFinalAnimated() {
		return isFinalAnimated;
	}

	public void setFinalAnimated(boolean isFinalAnimated) {
		this.isFinalAnimated = isFinalAnimated;
	}

	public void setActive(boolean isActive) {
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

	public Vector2 getDistance() {
		return distance;
	}
	
	public boolean isMarked() {
		return isMarked;
	}

	public void setMarked(boolean isMarked) {
		this.isMarked = isMarked;
	}

	public void updateDistance() {
		distance.y = 0;
		distance.x = 0;
	}

	@Override
	public void update(float dt) {
		if (!isActive)
			return;

		if (!isStatic) {
			setPosition(getX() + getVelocity().x - xOffset, getY() + getVelocity().y - yOffset);
			distance.x += Math.abs(getVelocity().x);
			distance.y += Math.abs(getVelocity().y);	
		}
		
		super.update(dt);
	}
	
	public void draw(){
		draw(batch);	
	}

	public void initSkill(SpriteBatch batch, Texture spriteTexture) {
		setSpriteBatch(batch);
		setSpriteTexture(spriteTexture);
	}

	public boolean isAOE() {
		return isAOE;
	}

	public void setAOE(boolean isAOE) {
		this.isAOE = isAOE;
	}
}
