package com.webgame.game.world.skills.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.world.skills.SkillObject;

public class MeteorRainObject extends SkillObject {
	protected Animation<TextureRegion> animation;
	protected TextureRegion standTexture;

	public MeteorRainObject() {
		super();
	}

	@Override
	public void initSkill(SpriteBatch batch, Texture spriteTexture) {
		this.setSpriteBatch(batch);
		this.setSpriteTexture(spriteTexture);

		xOffset = yOffset = 0;
		int h = 50;
		int w = 30;
		int l = 5;

		int h2 = 50;
		int w2 = 60;

		TextureRegion[] frames = new TextureRegion[l];

		for (int i = 0; i < l; i++)
			frames[i] = new TextureRegion(this.spriteTexture, 190 + w2 * (i), 110, w2, h2);

		standTexture = new TextureRegion(this.spriteTexture, 810, 55, w, h);
		animationMaxDuration = animationDuration * l;
		animation = new Animation<TextureRegion>(animationDuration, frames);

		int w3 = 20;
		int h3 = 20;
		this.setBounds(0, 0, w3 / PPM, h3 / PPM);
		setRegion(standTexture);
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
}
