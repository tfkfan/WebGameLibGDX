package com.webgame.game.world.skills.impl.skill_sprites;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.impl.MeteorBlastAnimation;
import com.webgame.game.world.skills.SkillSprite;

public class MeteorRainSprite extends SkillSprite {
	protected MeteorBlastAnimation animation;
	protected TextureRegion standTexture;

	public MeteorRainSprite() {
		super();
	}

	@Override
	public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
		this.setSpriteBatch(batch);
		this.setSpriteTexture(spriteTexture);

		xOffset = yOffset = 0;
		int h = 50;
		int w = 30;
		int l = 5;

		standTexture = new TextureRegion(this.spriteTexture, 810, 55, w, h);
		animationMaxDuration = animationDuration * l;
		animation = new MeteorBlastAnimation(spriteTexture, animationDuration, animationMaxDuration);

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
				region = animation.getAnimation().getKeyFrame(animateTimer, false);
			else
				region = standTexture;
		}
		return region;
	}
}
