package com.webgame.game.world.skills.impl.skill_sprites;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.impl.BlizzardFragmentAnimation;
import com.webgame.game.world.skills.SkillSprite;

public class BlizzardSprite extends SkillSprite {
	protected BlizzardFragmentAnimation animation;
	protected TextureRegion standTexture;

	public BlizzardSprite() {
		super();
	}

	@Override
	public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
		this.setSpriteBatch(batch);
		this.setSpriteTexture(spriteTexture);

		animationDuration = 0.1f;
		animationMaxDuration = animationDuration * 2;
		animation = new BlizzardFragmentAnimation(spriteTexture, animationDuration, animationMaxDuration);
		standTexture = new TextureRegion(this.spriteTexture, 5, 245, 30, 30);

		int w2 = 20;
		int h2 = 25;
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
