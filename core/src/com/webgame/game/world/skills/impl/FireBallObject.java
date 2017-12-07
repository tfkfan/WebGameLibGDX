package com.webgame.game.world.skills.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.world.skills.SkillObject;

public class FireBallObject extends SkillObject {
	protected TextureRegion standTexture;
	protected Animation<TextureRegion> animation;

	public FireBallObject() {
		super();
		animationMaxDuration = 1.7f;
		animationDuration = 0.1f;
	}

	@Override
	public void initSkill(SpriteBatch batch, Texture spriteTexture) {
		super.initSkill(batch, spriteTexture);

		int h = 50;
		int w = 30;
		int l = 1;

		TextureRegion[] frames = new TextureRegion[l];

		// Доделать
		for (int i = 0; i < l; i++)
			frames[i] = new TextureRegion(this.spriteTexture, 200, 155, w, h);

		standTexture = new TextureRegion(this.spriteTexture, 810, 50, w, h);
		
		h = 65;
		w = 65;
	    l = 16;
		TextureRegion[] animationFrames = new TextureRegion[l];

		for (int i = 0; i < l; i++)
			animationFrames[i] = new TextureRegion(spriteTexture, 4 + w * i, 165, w, h);

		animation = new Animation<TextureRegion>(animationDuration, animationFrames);
		animationMaxDuration = animationDuration * l;

		int w2 = 50;
		int h2 = 50;
		this.setBounds(0, 0, w2 / PPM, h2 / PPM);
		setRegion(standTexture);
	}

	@Override
	public TextureRegion getFrame() {
		TextureRegion region = null;
		
		if(isActive)
			 region = isStatic && !isFinalAnimated ? animation.getKeyFrame(animateTimer, false) : standTexture;

		return region;
	}

}
