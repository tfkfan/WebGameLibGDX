package com.webgame.game.world.skills.impl.skill_sprites;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.impl.FireBlastAnimation;
import com.webgame.game.world.skills.SkillSprite;

public class ExplosionSprite extends SkillSprite {
	protected FireBlastAnimation animation;

	public ExplosionSprite() {
		super();
	}

	@Override
	public void initSkill(SpriteBatch batch, Texture spriteTexture) {
		this.setSpriteBatch(batch);
		this.setSpriteTexture(spriteTexture);

		animationMaxDuration = 1.7f;
		animationDuration = 0.1f;
		animation = new FireBlastAnimation(spriteTexture, animationDuration, animationMaxDuration);
		int w2 = 100;
		int h2 = 100;
		this.setBounds(0, 0, w2 / PPM, h2 / PPM);
		setRegion(animation.getAnimation().getKeyFrame(0));
	}

	@Override
	public TextureRegion getFrame() {
		TextureRegion region = null;

		if (isActive)
			region = animation.getAnimation().getKeyFrame(animateTimer, false);

		return region;
	}
}
