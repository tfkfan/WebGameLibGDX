package com.webgame.client.animation.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.client.animation.GameAnimation;

public class FireBlastAnimation extends GameAnimation {

    public FireBlastAnimation(Texture spriteTexture) {
        super(spriteTexture, 0.1f);
    }

    @Override
    public void initAnimation() {
        int h = 65;
        int w = 65;
        int l = 16;
        TextureRegion[] animationFrames = new TextureRegion[l];

        for (int i = 0; i < l; i++)
            animationFrames[i] = new TextureRegion(getSpriteTexture(), 4 + w * i, 165, w, h);

        setAnimation(new Animation<TextureRegion>(getAnimationDuration(), animationFrames));
    }
}
