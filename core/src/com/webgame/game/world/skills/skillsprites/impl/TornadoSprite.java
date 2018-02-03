package com.webgame.game.world.skills.skillsprites.impl;

import static com.webgame.game.Configs.PPM;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.impl.TornadoAnimation;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public class TornadoSprite extends SkillSprite {
	protected TornadoAnimation animation;

	public TornadoSprite() {
		super();
	}

	@Override
	public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
		this.setSpriteBatch(batch);
		this.setSpriteTexture(spriteTexture);

		animation = new TornadoAnimation(spriteTexture, animationDuration, animationMaxDuration);

		int w2 = 100;
		int h2 = 100;
		this.setBounds(0, 0, w2 / PPM, h2 / PPM);
		setRegion(animation.getAnimation().getKeyFrame(0));
	}

	@Override
	public TextureRegion getFrame() {
		TextureRegion region = null;

		if (isActive)
			region = animation.getAnimation().getKeyFrame(animateTimer, true);

		return region;
	}

}
