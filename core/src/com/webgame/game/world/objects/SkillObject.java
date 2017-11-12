package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import static com.webgame.game.Configs.PPM;

public abstract class SkillObject extends GameObject {
	protected boolean isActive;
	protected boolean isStatic;
	protected boolean isFalling;
	protected boolean isFinalAnimated;
	protected boolean isAOE;

	protected final Vector2 distance;
	protected Vector2 targetPosition;
	protected Vector2 playerPosition;
	
	protected Rectangle area;

	protected final Vector2 fallVelocity;
	protected final Vector2 fallOffsetVec;
	protected final float fallOffset;
	
	protected final float animationDuration = 0.2f;
	protected float animateTimer = 0;

	public SkillObject() {
		super();
		distance = new Vector2(0, 0);
		fallOffset = 1f / PPM;
		fallVelocity = new Vector2(2 / PPM, 4 / PPM);
		fallOffsetVec = new Vector2(fallVelocity.x * 25, fallVelocity.y * 25);
	}

	public Rectangle getArea() {
		return area;
	}

	public void setArea(Rectangle area) {
		this.area = area;
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
	public void update(float dt) {
		if (!isActive)
			return;

		preMove(dt);

		if (!isStatic) {
			if (!isFalling) {
				setPosition(getX() + getVelocity().x - xOffset, getY() + getVelocity().y - yOffset);
				distance.x += getVelocity().x;
				distance.y += getVelocity().y;
			} else {
				setPosition(getX() + fallVelocity.x - xOffset, getY() - fallVelocity.y - yOffset);
				distance.x += fallVelocity.x;
				distance.y += fallVelocity.y;
			}
		}
		
		if(animateTimer > animationDuration* 3){
			animateTimer = 0;
			isFinalAnimated = true;
		}
		if(isStatic && !isFinalAnimated)
			animateTimer += dt;
		
		super.update(dt);

		afterMove();
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

	public void initPositions() {
		updateDistance();
		if (isFalling || isStatic) {
			if (isFalling) {
				float x = getRandomPos(area.getX(), area.getX() + area.getWidth());
				float y = getRandomPos(area.getY(), area.getY() + area.getHeight());
				if (!isStatic)
					setPosition(x - fallOffsetVec.x - xOffset, y + fallOffsetVec.y - yOffset);
			}
		} else
			setPosition(playerPosition.x - xOffset, playerPosition.y - yOffset);
	}

	public void preMove(float dt) {

	}

	public void afterMove() {
		if (isFalling) {
			if (distance.y > fallOffsetVec.y) {
				if (!isFinalAnimated) {
					isStatic = true;
				} else {
					isStatic = false;
					isFinalAnimated = false;
					initPositions();
				}

			}
		}
		float x = getX();
		float y = getY();
		if (x < -10 || y < -10 || x > 10 || y > 10){
			setActive(false);
		}
	}

	public float getRandomPos(float min, float max) {
		Double random = min + Math.random() * (max - min);
		return random.floatValue();
	}
}
