package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteTextureLoader {
	protected Texture spriteTexture;

	protected SpriteBatch batch;

	public SpriteTextureLoader() {

	}

	public SpriteTextureLoader(String spritePath) {
		loadSprite(spritePath);
	}

	public SpriteTextureLoader(SpriteBatch batch) {
		setSpriteBatch(batch);
	}

	public SpriteTextureLoader(SpriteBatch batch, String spritePath) {
		setSpriteBatch(batch);
		loadSprite(spritePath);
	}

	public void loadSprite(String spritePath) {
		spriteTexture = new Texture(Gdx.files.internal(spritePath));
	}

	public void setSpriteBatch(SpriteBatch batch) {
		this.batch = batch;
	}
	
	public SpriteBatch getSpriteBatch() {
		return this.batch;
	}

	public Texture getSpriteTexture() {
		return spriteTexture;
	}

	public void setSpriteTexture(Texture spriteTexture) {
		this.spriteTexture = spriteTexture;
	}

}
