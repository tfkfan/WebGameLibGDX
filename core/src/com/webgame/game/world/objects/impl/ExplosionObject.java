package com.webgame.game.world.objects.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.state.State;
import com.webgame.game.world.objects.SkillObject;

public class ExplosionObject extends SkillObject {
	protected Animation<TextureRegion> animation;

	public ExplosionObject() {
		super();
		animationMaxDuration = 1.7f;
		animationDuration = 0.1f;
	}

	@Override
	public void initSkill(SpriteBatch batch, Texture spriteTexture) {
		this.setSpriteBatch(batch);
		this.setSpriteTexture(spriteTexture);

		int h = 65;
		int w = 65;
		int l = 16;

		TextureRegion[] frames = new TextureRegion[l];

		for (int i = 0; i < l; i++)
			frames[i] = new TextureRegion(spriteTexture,4 + w * i, 165, w, h);

		animation = new Animation<TextureRegion>(animationDuration, frames);

		int w2 = 100;
		int h2 = 100;
		this.setBounds(0, 0, w2 / PPM, h2 / PPM);
		setRegion(animation.getKeyFrame(0));
	}

	@Override
	public State getState() {
		return currState;
	}

	@Override
	public TextureRegion getFrame() {
		TextureRegion region = null;

		if (isActive)
			region = animation.getKeyFrame(animateTimer, false);
		
		return region;
	}
}
