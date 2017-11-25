package com.webgame.game.world.skills.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.state.State;
import com.webgame.game.world.skills.SkillObject;

public class TornadoObject extends SkillObject {
	protected Animation<TextureRegion> animation;

	public TornadoObject() {
		super();
	}

	@Override
	public void initSkill(SpriteBatch batch, Texture spriteTexture) {
		this.setSpriteBatch(batch);
		this.setSpriteTexture(spriteTexture);

		int h = 60;
		int w = 65;
		int l = 4;

		TextureRegion[] frames = new TextureRegion[l];

		for (int i = 0; i < l; i++)
			frames[i] = new TextureRegion(spriteTexture,592 + w * (i + 1), 295, 60, h);

		animation = new Animation<TextureRegion>(animationDuration, frames);

		int w2 = 100;
		int h2 = 100;
		this.setBounds(0, 0, w2 / PPM, h2 / PPM);
		setRegion(animation.getKeyFrame(0));
	}

	@Override
	public TextureRegion getFrame() {
		TextureRegion region = null;

		if (isActive)
			region = animation.getKeyFrame(animateTimer, true);
		
		return region;
	}

}
