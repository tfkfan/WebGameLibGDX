package com.webgame.game.animation.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.GameAnimation;

public class LightningAnimation extends GameAnimation {


    public LightningAnimation(Texture spriteTexture) {
        super(spriteTexture, 0.05f);
    }

    @Override
    public void initAnimation() {
        int h = 700;
        int w = 700;
        int l = 4;

        TextureRegion[] frames = new TextureRegion[l];

        for (int i = 0, k = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++, k++)
                frames[k] = new TextureRegion(getSpriteTexture(), w * i, h * j, w, h);
        }
        setAnimation(new Animation<TextureRegion>(getAnimationDuration(), frames));

    }
}
