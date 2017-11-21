package com.webgame.game.world.skills.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.state.State;
import com.webgame.game.world.skills.SkillObject;

public class FireBallObject extends SkillObject {
	protected TextureRegion standTexture;

	public FireBallObject() {
		super();
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

		int w2 = 30;
		int h2 = 50;
		this.setBounds(0, 0, w2 / PPM, h2 / PPM);
		setRegion(standTexture);
	}

	@Override
	public State getState() {
		return currState;
	}

	@Override
	public TextureRegion getFrame() {
		TextureRegion region = isActive ? standTexture : null;

		return region;
	}

}
