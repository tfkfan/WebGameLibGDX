package com.webgame.game.animation.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.GameAnimation;

public class BlizzardFragmentAnimation extends GameAnimation {

    public BlizzardFragmentAnimation(Texture spriteTexture){
        super(spriteTexture, 0.1f);
    }

    @Override
    public void initAnimation() {
        int h = 30;
        int w = 30;
        int l = 3;

        TextureRegion[] frames = new TextureRegion[l];

        for (int i = 0; i < l; i++)
            frames[i] = new TextureRegion(getSpriteTexture(), 5 + w * (i + 1), 245, w, h);

        setAnimation(new Animation<TextureRegion>(getAnimationDuration(), frames));

    }
}
