package com.webgame.game.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BlizzardFragmentAnimation extends GameAnimation {

    public BlizzardFragmentAnimation(Texture spriteTexture) {
        super(spriteTexture);
    }

    public BlizzardFragmentAnimation(Texture spriteTexture, Float animationDuration, Float animationMaxDuration){
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
        int h = 30;
        int w = 30;
        int l = 3;

        TextureRegion[] frames = new TextureRegion[l];

        for (int i = 0; i < l; i++)
            frames[i] = new TextureRegion(this.spriteTexture, 5 + w * (i + 1), 245, w, h);

        setAnimation(new Animation<TextureRegion>(animationDuration, frames));

    }
}
