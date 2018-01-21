package com.webgame.game.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TornadoAnimation extends GameAnimation {

    public TornadoAnimation(Texture spriteTexture) {
        super(spriteTexture);
    }

    public TornadoAnimation(Texture spriteTexture, Float animationDuration, Float animationMaxDuration){
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
        int h = 60;
        int w = 64;
        int l = 4;

        TextureRegion[] frames = new TextureRegion[l];

        for (int i = 0; i < l; i++)
            frames[i] = new TextureRegion(spriteTexture, 594 + w * (i + 1), 295, 60, h);

        setAnimation(new Animation<TextureRegion>(animationDuration, frames));

    }
}
