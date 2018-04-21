package com.webgame.client.animation.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.client.animation.GameAnimation;

public class StoneBlastAnimation extends GameAnimation {

    public StoneBlastAnimation(Texture spriteTexture, Float animationDuration) {
        super(spriteTexture, animationDuration);
    }

    @Override
    public void initAnimation() {
        int h = 30;
        int w = 40;
        int l = 3;

        TextureRegion[] animationFrames = new TextureRegion[l];

        // ��������
        for (int i = 0; i < l; i++)
            animationFrames[i] = new TextureRegion(getSpriteTexture(), -5 + w * (i + 1), 80, w, h);

        setAnimation(new Animation<TextureRegion>(getAnimationDuration(), animationFrames));
    }
}
