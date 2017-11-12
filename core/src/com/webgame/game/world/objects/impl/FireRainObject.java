package com.webgame.game.world.objects.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.state.State;
import com.webgame.game.world.objects.SkillObject;

public class FireRainObject extends SkillObject {
	protected Animation<TextureRegion> animation;
	protected TextureRegion standTexture;

	public FireRainObject() {
		super();
	}

	@Override
	public void initSkill(SpriteBatch batch, Texture spriteTexture) {
		this.setSpriteBatch(batch);
		this.setSpriteTexture(spriteTexture);

		xOffset = yOffset = 0;
		int h = 50;
		int w = 30;
		int l = 6;

		int h2 = 50;
		int w2 = 60;

		TextureRegion[] frames = new TextureRegion[l];

		// Доделать
		for (int i = 0; i < l; i++)
			frames[i] = new TextureRegion(this.spriteTexture, 190 + w2 * (i + 1), 110, w2, h2);

		standTexture = new TextureRegion(this.spriteTexture, 810, 55, w, h);

		animation = new Animation<TextureRegion>(0.2f, frames);

		int w3 = 20;
		int h3 = 20;
		this.setBounds(0, 0, w3 / PPM, h3 / PPM);
		setRegion(standTexture);
	}

	@Override
	public State getState() {
		return currState;
	}

	@Override
	public TextureRegion getFrame() {
		TextureRegion region = null;
		if (isActive) {
			if (isStatic && !isFinalAnimated)
				region = animation.getKeyFrame(animateTimer, false);
			else
				region = standTexture;
		}
		return region;
	}

	@Override
	public void afterMove() {
		super.afterMove();
	}

}
