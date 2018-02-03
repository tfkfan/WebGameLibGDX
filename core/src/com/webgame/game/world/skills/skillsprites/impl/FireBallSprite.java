package com.webgame.game.world.skills.skillsprites.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.impl.FireBlastAnimation;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public class FireBallSprite extends SkillSprite {

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
            frames[i] = new TextureRegion(getSpriteTexture(), 200, 155, w, h);

        setStandTexture(new TextureRegion(getSpriteTexture(), 810, 50, w, h));

        setAnimationDuration(0.1f);
        setGameAnimation(new FireBlastAnimation(getSpriteTexture(), getAnimationDuration()));

        setAnimationMaxDuration(getAnimationDuration() * getGameAnimation().getAnimation().getKeyFrames().length);

        int w2 = 50;
        int h2 = 50;
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
