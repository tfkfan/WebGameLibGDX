package com.webgame.common.client.animation.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.common.client.animation.GameAnimation;

public class TornadoAnimation extends GameAnimation {

    public TornadoAnimation(Texture spriteTexture) {
        super(spriteTexture, 0.1f);
    }

    @Override
    public void initAnimation() {
        int h = 60;
        int w = 64;
        int l = 4;

        TextureRegion[] frames = new TextureRegion[l];

        for (int i = 0; i < l; i++)
            frames[i] = new TextureRegion(getSpriteTexture(), 594 + w * (i + 1), 295, 60, h);

        setAnimation(new Animation<TextureRegion>(getAnimationDuration(), frames));

    }
}
