package com.webgame.common.client.animation.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.common.client.animation.GameAnimation;

public class HealAnimation extends GameAnimation {
    public HealAnimation(Texture spriteTexture) {
        super(spriteTexture);
    }
/*
    public HealAnimation(Texture spriteTexture, Float animationDuration) {
        super(spriteTexture, animationDuration);
    }

    @Override
    public void initAnimation() {
        int h = 190;
        int w = 192;
        int l = 4;
        int l2 = 5;
        TextureRegion[] animationFrames = new TextureRegion[l * l2];


        for (int k = 0, i = 0; i < l; i++) {
            for (int j = 0; j < l2; j++, k++)
                animationFrames[k] = new TextureRegion(getSpriteTexture(), w * j, h * i, w, h);
        }
        //  animationFrames[0] = new TextureRegion(spriteTexture, 0 , 0, w, h);
        setAnimation(new Animation<TextureRegion>(getAnimationDuration(), animationFrames));
    }*/
}
