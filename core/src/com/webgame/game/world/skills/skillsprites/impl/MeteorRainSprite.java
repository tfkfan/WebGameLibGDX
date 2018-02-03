package com.webgame.game.world.skills.skillsprites.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.impl.MeteorBlastAnimation;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public class MeteorRainSprite extends SkillSprite {
    public MeteorRainSprite() {
        super();
    }

    @Override
    public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
        this.setSpriteBatch(batch);
        this.setSpriteTexture(spriteTexture);

        int h = 50;
        int w = 30;

        setStandTexture(new TextureRegion(getSpriteTexture(), 810, 55, w, h));
        setAnimationDuration(0.1f);
        setGameAnimation(new MeteorBlastAnimation(getSpriteTexture(), getAnimationDuration()));

        setAnimationMaxDuration(getAnimationDuration() * getGameAnimation().getAnimation().getKeyFrames().length);

        int w3 = 20;
        int h3 = 20;
        this.setBounds(0, 0, w3 / PPM, h3 / PPM);
        setRegion(getStandTexture());
    }

    @Override
    public TextureRegion getFrame() {
        TextureRegion region = null;
        if (isActive()) {
            if (isStatic() && !isFinalAnimated())
                region = getGameAnimation().getAnimation().getKeyFrame(getAnimateTimer(), false);
            else
                region = getStandTexture();
        }
        return region;
    }
}
