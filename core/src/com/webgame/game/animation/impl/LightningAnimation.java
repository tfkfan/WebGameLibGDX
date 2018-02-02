package com.webgame.game.animation.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.GameAnimation;

public class LightningAnimation extends GameAnimation {

    public LightningAnimation(Texture spriteTexture) {
        super(spriteTexture);
    }

    public LightningAnimation(Texture spriteTexture, Float animationDuration, Float animationMaxDuration){
        super(spriteTexture,  animationDuration, animationMaxDuration);
    }

    public Texture getSpriteTexture() {
        return spriteTexture;
    }

    public void setSpriteTexture(Texture spriteTexture) {
        this.spriteTexture = spriteTexture;

    }

    @Override
    public void initAnimation() {
        int h = 700;
        int w = 700;
        int l = 4;

        TextureRegion[] frames = new TextureRegion[l];

        for (int i = 0, k = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++, k++)
                frames[k] = new TextureRegion(spriteTexture, w * i, h * j, w, h);
        }

        animation = new Animation<TextureRegion>(animationDuration, frames);

        setAnimation(new Animation<TextureRegion>(animationDuration, frames));

    }
}
