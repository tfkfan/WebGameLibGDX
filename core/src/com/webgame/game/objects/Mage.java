package com.webgame.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Mage extends Player {

	public Mage(String spritePath) {
		super(spritePath);

		Array<TextureRegion> frames = new Array<TextureRegion>();
		int h = 61;
		int w = 72;
		for (int i = 0; i < 11; i++)
			frames.add(new TextureRegion(spriteTexture, 0, h * i, w, h));

		setWalkAnimation(new Animation<TextureRegion>(0.2f, frames));
	}

	@Override
	public void draw(SpriteBatch batch, float dt) {
		//sprite.draw(batch);
		updateStateTimer(dt);
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTimer, true);
		batch.draw(currentFrame, 50, 50);
	}

}
