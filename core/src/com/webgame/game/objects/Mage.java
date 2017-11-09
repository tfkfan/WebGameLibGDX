package com.webgame.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Mage extends Player {

	protected Array<Animation<TextureRegion>> animations;
	protected TextureRegion[] standRegions;

	public Mage(String spritePath) {
		super(spritePath);

		final int dirs = 8;
		
		TextureRegion[][] frames = new TextureRegion[dirs][5];
		animations = new Array<Animation<TextureRegion>>();
		standRegions = new TextureRegion[dirs];

		
		
		int h = 61;
		int w = 72;

		for (int i = 0; i < dirs; i++)
			for (int j = 0; j < 5; j++)
				frames[i][j] = new TextureRegion(spriteTexture, w * i, h * j, w, h);

		for(int i = 0; i < dirs; i++){
			animations.add(new Animation<TextureRegion>(0.2f, frames[i]));
			standRegions[i] = new TextureRegion(spriteTexture, w * i, 0, w, h);
		}
	}

	@Override
	public void draw(SpriteBatch batch, float dt) {
		// sprite.draw(batch);
		updateStateTimer(dt);
		TextureRegion currentFrame = getFrame();
		batch.draw(currentFrame, position.x, position.y);
	}

	@Override
	public TextureRegion getFrame() {
		prevState = currState;
		currState = getState();

		TextureRegion region;
		Animation<TextureRegion> animation = null;
		TextureRegion standRegion;
		Integer index = 0;

		switch (direction) {
		case UP:
			index = 0;
			break;
		case UPRIGHT:
			index = 1;
			break;
		case RIGHT:
			index = 2;
			break;
		case RIGHTDOWN:
			index = 3;
			break;
		case DOWN:
			index = 4;
			break;
		case LEFTDOWN:
			index = 5;
			break;
		case LEFT:
			index = 6;
			break;
		case UPLEFT:
			index = 7;
			break;
		default:
			index = 0;
			break;
		}
		
		animation = animations.get(index);
		standRegion = standRegions[index];

		switch (currState) {
		case WALK:
			region = animation.getKeyFrame(stateTimer, true);
			break;
		case STAND:
		default:
			region = standRegion;
			break;
		}

		return region;
	}
}
