package com.webgame.game.world.skills.impl.skill_sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.impl.HealAnimation;
import com.webgame.game.animation.impl.TeleportAnimation;
import com.webgame.game.world.skills.SkillSprite;

import static com.webgame.game.Configs.PPM;

public class HealSprite extends SkillSprite {
	protected HealAnimation animation;

	public HealSprite() {
		super();
	}

	@Override
	public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
		this.setSpriteBatch(batch);
		this.setSpriteTexture(spriteTexture);


		animationDuration = 0.1f;
		animation = new HealAnimation(spriteTexture, animationDuration, animationMaxDuration);
		animationMaxDuration = animationDuration*animation.getAnimation().getKeyFrames().length;
		int w2 = 100;
		int h2 = 80;
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