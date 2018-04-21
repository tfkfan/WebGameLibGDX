package com.webgame.client.animation.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.client.animation.GameAnimation;

public class BuffAnimation extends GameAnimation {
    public BuffAnimation(Texture spriteTexture) {
        super(spriteTexture, 0.05f);
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
    }
}
