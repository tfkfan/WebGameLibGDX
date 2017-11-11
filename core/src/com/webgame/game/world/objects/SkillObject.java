package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import static com.webgame.game.Configs.PPM;

public abstract class SkillObject extends GameObject {
	protected boolean isActive;
	protected boolean isStatic;
	protected boolean isFalling;

	protected final Vector2 distance;
	protected Vector2 targetPosition;
	protected Vector2 playerPosition;

	protected final Vector2 fallVelocity;
	protected final Vector2 fallOffsetVec;
	protected final float fallOffset;

	public SkillObject() {
		super();
		distance = new Vector2(0, 0);
		fallOffset = 1f / PPM;
		fallVelocity = new Vector2(1 / PPM, 2/PPM);
		fallOffsetVec = new Vector2(fallVelocity.x * 50, fallVelocity.y * 50);
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

	public void updateDistance() {
		distance.y = 0;
		distance.x = 0;
	}

	public Vector2 getTargetPosition() {
		return targetPosition;
	}

	public void setTargetPosition(Vector2 targetPosition) {
		this.targetPosition = targetPosition;
	}

	public Vector2 getPlayerPosition() {
		return playerPosition;
	}

	public void setPlayerPosition(Vector2 playerPosition) {
		this.playerPosition = playerPosition;
	}

	@Override
	public void move(float dt) {
		if (!isActive)
			return;

		preMove(dt);

		if (!isFalling && !isStatic) {
			setPosition(getX() + getVelocity().x - xOffset, getY() + getVelocity().y - yOffset);
			distance.x += getVelocity().x;
			distance.y += getVelocity().y;
		} else if (isFalling) {
			setPosition(getX() + fallVelocity.x - xOffset, getY() - fallVelocity.y - yOffset);
			distance.x += fallVelocity.x;
			distance.y += fallVelocity.y;
		}
		super.move(dt);

		afterMove();
	}

	public void initSkill(SpriteBatch batch, String spritePath) {
		setSpriteBatch(batch);
		setSpriteTexture(spritePath);
	}

	public void initPositions() {
		updateDistance();
		if (isFalling || isStatic) {
			if (isFalling){
				float x = getRandomPos(targetPosition.x - 50/PPM, targetPosition.x +50/PPM);
				float y = getRandomPos(targetPosition.y - 50/PPM, targetPosition.y + 50/PPM);
				
				setPosition(x - fallOffsetVec.x - xOffset, y + fallOffsetVec.y - yOffset);
			}
		} else
			setPosition(playerPosition.x - xOffset, playerPosition.y - yOffset);
	}

	public void preMove(float dt) {

	}

	public void afterMove() {
		if (isFalling) {
			if (distance.y > fallOffsetVec.y)
				initPositions();
		}
		float x = getX();
		float y = getY();
		if (x < -10 || y < -10 || x > 10 || y > 10)
			setActive(false);
	}

	public float getRandomPos(float min, float max) {
		Double random = min + Math.random() * (max - min);
		return random.floatValue();
	}
}
