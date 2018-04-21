package com.webgame.client.animation.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.client.animation.GameAnimation;

public class MeteorBlastAnimation extends GameAnimation {
    public MeteorBlastAnimation(Texture spriteTexture, Float animationDuration) {
        super(spriteTexture, animationDuration);
    }

    @Override
    public void initAnimation() {
        int h2 = 50;
        int w2 = 60;
        int l = 5;

        TextureRegion[] frames = new TextureRegion[l];

        for (int i = 0; i < l; i++)
            frames[i] = new TextureRegion(getSpriteTexture(), 190 + w2 * (i), 110, w2, h2);

        setAnimation(new Animation<TextureRegion>(getAnimationDuration(), frames));

    }
}
