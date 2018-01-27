package com.webgame.game.world.skills.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.StoneBlastAnimation;
import com.webgame.game.world.skills.SkillSprite;

public class StoneRainSprite extends SkillSprite {
	protected StoneBlastAnimation animation;
	protected TextureRegion standTexture;

	public StoneRainSprite() {
		super();
	}

	@Override
	public void initSkill(SpriteBatch batch, Texture spriteTexture) {
		this.setSpriteBatch(batch);
		this.setSpriteTexture(spriteTexture);

		standTexture = new TextureRegion(spriteTexture, 5, 80, 40, 30);
		animation = new StoneBlastAnimation(spriteTexture, animationDuration, animationMaxDuration);

		int w2 = 20;
		int h2 = 20;
		this.setBounds(0, 0, w2 / PPM, h2 / PPM);
		setRegion(standTexture);
	}

	@Override
	public TextureRegion getFrame() {
		TextureRegion region = null;

		if (isActive)
			region = isStatic && !isFinalAnimated ? animation.getAnimation().getKeyFrame(animateTimer, false) : standTexture;

		return region;
	}

}
