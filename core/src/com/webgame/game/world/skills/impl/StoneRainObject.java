package com.webgame.game.world.skills.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.state.State;
import com.webgame.game.world.skills.SkillObject;

public class StoneRainObject extends SkillObject {
	protected Animation<TextureRegion> animation;
	protected TextureRegion standTexture;

	public StoneRainObject() {
		super();
	}

	@Override
	public void initSkill(SpriteBatch batch, Texture spriteTexture) {
		this.setSpriteBatch(batch);
		this.setSpriteTexture(spriteTexture);

		int h = 30;
		int w = 40;
		int l = 3;

		TextureRegion[] frames = new TextureRegion[l];

		// Доделать
		for (int i = 0; i < l; i++)
			frames[i] = new TextureRegion(spriteTexture, -5 + w * (i + 1), 80, w, h);

		standTexture = new TextureRegion(spriteTexture, 5, 80, w, h);

		animation = new Animation<TextureRegion>(animationDuration, frames);

		int w2 = 20;
		int h2 = 20;
		this.setBounds(0, 0, w2 / PPM, h2 / PPM);
		setRegion(standTexture);
	}

	@Override
	public State getState() {
		return currState;
	}

	@Override
	public TextureRegion getFrame() {
		TextureRegion region = null;

		if (isActive)
			region = isStatic && !isFinalAnimated ? animation.getKeyFrame(animateTimer, false) : standTexture;

		return region;
	}

}
