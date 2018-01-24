package com.webgame.game.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.webgame.game.utils.SpriteTextureLoader;

public abstract class GameActor extends Actor implements Movable, Animated {
	protected Vector2 velocity;

	protected float xOffset;
	protected float yOffset;

	protected static final int dirs = 8;

	public GameActor(){

	}

	protected void init(){
		velocity = new Vector2(0,0);
		xOffset = yOffset = 0;
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

	@Override
	public void draw(Batch batch, float parentAlpha){
		TextureRegion tr = getFrame();
		batch.draw(getFrame(), getX() - getXOffset(), getY() - getYOffset(), getWidth(), getHeight());
	}
}
