package com.webgame.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player implements GameObject {

	protected Texture spriteTexture;
	protected Sprite sprite;

	protected Vector2 position;
	protected Vector2 velosity;

	float stateTimer;

	Animation<TextureRegion> walkAnimation;
	Integer FRAME_COLS = 5;
	Integer FRAME_ROWS = 11;

	public Player(String spritePath) {
		spriteTexture = new Texture(Gdx.files.internal(spritePath));

		Array<TextureRegion> frames = new Array<TextureRegion>();
		int index = 0;
		stateTimer = 0;
		int h = 61;
		int w = 72;
		for (int i = 0; i < 11; i++)
			frames.add(new TextureRegion(spriteTexture, 0, h * i, w, h));

		// Initialize the Animation with the frame interval and array of frames
		walkAnimation = new Animation<TextureRegion>(0.2f, frames);

		sprite = new Sprite(spriteTexture, 20, 20, 50, 50);
		sprite.setPosition(10, 10);
	}

	@Override
	public void draw(SpriteBatch batch, float dt) {
		stateTimer += dt;
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTimer, true);
		batch.draw(currentFrame, 50, 50);
		sprite.draw(batch);
	}
}
