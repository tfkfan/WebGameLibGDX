package com.webgame.game.world.skills.skillsprites.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.impl.IceBlastAnimation;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

import static com.webgame.game.Configs.PPM;

public class IceExplosionSprite extends SkillSprite {
    public IceExplosionSprite() {
        super();
    }

    @Override
    public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
        this.setSpriteBatch(batch);
        this.setSpriteTexture(spriteTexture);


        setAnimationDuration(0.1f);
        setGameAnimation(new IceBlastAnimation(getSpriteTexture(), getAnimationDuration()));

        setAnimationMaxDuration(getAnimationDuration() * getGameAnimation().getAnimation().getKeyFrames().length);
        int w2 = 300;
        int h2 = 200;
        this.setBounds(0, 0, w2 / PPM, h2 / PPM);
        setRegion(getGameAnimation().getAnimation().getKeyFrame(0));
    }

    @Override
    public TextureRegion getFrame() {
        TextureRegion region = null;

        if (isActive())
            region = getGameAnimation().getAnimation().getKeyFrame(getAnimateTimer(), false);

        return region;
    }
}
