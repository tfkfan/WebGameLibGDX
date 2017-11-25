package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.state.State;
import com.webgame.game.world.objects.impl.SpriteTextureLoader;

public abstract class GameObject extends Sprite implements Movable, Animated {
	protected Vector2 velocity;

	protected float stateTimer;


	protected float xOffset;
	protected float yOffset;

	protected final int dirs = 8;
	protected final float absVelocity = 5f;

	protected SpriteBatch batch;
	protected Texture spriteTexture;

	public GameObject() {
		init();
	}

	public void init() {
		xOffset = yOffset = 0;
		velocity = new Vector2();
		stateTimer = 0;
	}

	public Texture getSpriteTexture() {
		return spriteTexture;
	}

	public void setSpriteTexture(Texture spriteTexture) {
		this.spriteTexture = spriteTexture;
	}

	public void setSpriteTexture(String spritePath) {
		this.spriteTexture = SpriteTextureLoader.loadSprite(spritePath);
	}

	public void setSpriteBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public float getXOffset() {
		return xOffset;
	}

	public void setXOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getYOffset() {
		return yOffset;
	}

	public void setYOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	public float getStateTimer() {
		return stateTimer;
	}

	public void setStateTimer(float stateTimer) {
		this.stateTimer = stateTimer;
	}


	protected void updateStateTimer(float dt) {
		stateTimer += dt;
	}



	@Override
	public void update(float dt) {
		updateStateTimer(dt);
		setRegion(getFrame());
	}

	public void drawShape(ShapeRenderer sr) {
		sr.rect(getX(), getY(), getWidth(), getHeight());
	}
}
