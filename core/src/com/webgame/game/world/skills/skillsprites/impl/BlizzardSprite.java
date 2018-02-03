package com.webgame.game.world.skills.skillsprites.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.impl.BlizzardFragmentAnimation;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public class BlizzardSprite extends SkillSprite {
    public BlizzardSprite() {
        super();
    }

    @Override
    public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
        this.setSpriteBatch(batch);
        this.setSpriteTexture(spriteTexture);

        setAnimationDuration(0.1f);
        setGameAnimation(new BlizzardFragmentAnimation(spriteTexture, getAnimationDuration()));
        setAnimationMaxDuration(getAnimationDuration() * getGameAnimation().getAnimation().getKeyFrames().length);
        setStandTexture(new TextureRegion(getSpriteTexture(), 5, 245, 30, 30));

        int w2 = 20;
        int h2 = 25;
        this.setBounds(0, 0, w2 / PPM, h2 / PPM);
        setRegion(getStandTexture());
    }

    @Override
    public TextureRegion getFrame() {
        TextureRegion region = null;

        if (isActive())
            region = isStatic() && !isFinalAnimated() ? this.getGameAnimation().getAnimation().getKeyFrame(getAnimateTimer(), false) : getStandTexture();

        return region;
    }

}
