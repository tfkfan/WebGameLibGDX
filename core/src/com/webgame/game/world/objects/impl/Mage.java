package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.webgame.game.state.PlayerState;
import com.webgame.game.world.objects.Player;

import static com.webgame.game.Configs.PPM;

public class Mage extends Player {

	protected Array<Animation<TextureRegion>> animations;
	protected TextureRegion[] standRegions;

	public Mage(SpriteBatch batch, String spritePath) {
		super(batch, spritePath);

		setXOffset(30 / PPM);
		setYOffset(15 / PPM);
		
		Texture spriteTexture = getSpriteTextureLoader().getSpriteTexture();

		TextureRegion[][] frames = new TextureRegion[dirs][5];
		animations = new Array<Animation<TextureRegion>>();
		standRegions = new TextureRegion[dirs];

		int h = 61;
		int w = 72;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++)
				frames[i][j] = new TextureRegion(spriteTexture, w * i, h * j, w, h);
			standRegions[i] = new TextureRegion(spriteTexture, w * i, 0, w, h);
		}

		for (int j = 0; j < 5; j++) {

			TextureRegion tr = new TextureRegion(spriteTexture, w, h * j, w, h);
			tr.flip(true, false);
			frames[7][j] = tr;

			if (j == 0)
				standRegions[7] = tr;

			tr = new TextureRegion(spriteTexture, w * 2, h * j, w, h);
			tr.flip(true, false);
			frames[6][j] = tr;

			if (j == 0)
				standRegions[6] = tr;

			tr = new TextureRegion(spriteTexture, w * 3, h * j, w, h);
			tr.flip(true, false);
			frames[5][j] = tr;

			if (j == 0)
				standRegions[5] = tr;
		}

		for (int i = 0; i < dirs; i++) {
			Animation<TextureRegion> anim = new Animation<TextureRegion>(0.2f, frames[i]);
			animations.add(anim);
			frames[i] = null;
		}

		setRegion(standRegions[0]);
	}

	@Override
	public TextureRegion getFrame() {
		prevState = currState;
		currState = getState();

		TextureRegion region;
		Animation<TextureRegion> animation = null;
		TextureRegion standRegion;
		Integer index = getDirectionIndex();

		animation = animations.get(index);
		standRegion = standRegions[index];

		switch ((PlayerState)currState) {
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
