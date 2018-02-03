package com.webgame.game.world.skills.skillsprites.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.impl.StoneBlastAnimation;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public class StoneRainSprite extends SkillSprite {
    public StoneRainSprite() {
        super();
    }

    @Override
    public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
        this.setSpriteBatch(batch);
        this.setSpriteTexture(spriteTexture);

        setStandTexture(new TextureRegion(spriteTexture, 5, 80, 40, 30));

        setAnimationDuration(0.1f);
        setGameAnimation(new StoneBlastAnimation(getSpriteTexture(), getAnimationDuration()));

        setAnimationMaxDuration(getAnimationDuration() * getGameAnimation().getAnimation().getKeyFrames().length);
        int w2 = 20;
        int h2 = 20;
        this.setBounds(0, 0, w2 / PPM, h2 / PPM);
        setRegion(getStandTexture());
    }

    @Override
    public TextureRegion getFrame() {
        TextureRegion region = null;

        if (isActive())
            region = isStatic() && !isFinalAnimated() ? getGameAnimation().getAnimation().getKeyFrame(getAnimateTimer(), false) : getStandTexture();

        return region;
    }

}
