package com.webgame.game.world.objects.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.state.State;
import com.webgame.game.world.objects.SkillObject;

public class LightningObject extends SkillObject {
	protected Animation<TextureRegion> animation;

	public LightningObject() {
		super();
	}

	@Override
	public void initSkill(SpriteBatch batch, Texture spriteTexture) {
		this.setSpriteBatch(batch);
		this.setSpriteTexture(spriteTexture);

		int h = 700;
		int w = 700;
		int l = 4;

		TextureRegion[] frames = new TextureRegion[l];

		for (int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++)
				frames[i + j] = new TextureRegion(spriteTexture,w * i, h * j, w, h);
		}

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
			region = animation.getKeyFrame(animateTimer, true);
		
		return region;
	}

}
