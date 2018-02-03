package com.webgame.game.world.skills.impl.skill_sprites;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.impl.FireBlastAnimation;
import com.webgame.game.world.skills.SkillSprite;

public class FireBallSprite extends SkillSprite {
	protected TextureRegion standTexture;
	protected FireBlastAnimation animation;

	public FireBallSprite() {
		super();
	}

	@Override
	public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
		super.initSkillSprite(batch, spriteTexture);

		int h = 50;
		int w = 30;
		int l = 1;

		TextureRegion[] frames = new TextureRegion[l];

		for (int i = 0; i < l; i++)
			frames[i] = new TextureRegion(this.spriteTexture, 200, 155, w, h);

		standTexture = new TextureRegion(this.spriteTexture, 810, 50, w, h);

		animation = new FireBlastAnimation(spriteTexture);
		animationDuration = 0.1f;
		animation.setAnimationDuration(animationDuration);
		animation.initAnimation();
		animationMaxDuration = animationDuration * animation.getAnimation().getKeyFrames().length;
		animation.setAnimationMaxDuration(animationMaxDuration);


		int w2 = 50;
		int h2 = 50;
		this.setBounds(0, 0, w2 / PPM, h2 / PPM);
		setRegion(standTexture);
	}

	@Override
	public TextureRegion getFrame() {
		TextureRegion region = null;
		
		if(isActive)
			 region = isStatic && !isFinalAnimated ? animation.getAnimation().getKeyFrame(animateTimer, false) : standTexture;

		return region;
	}

}
