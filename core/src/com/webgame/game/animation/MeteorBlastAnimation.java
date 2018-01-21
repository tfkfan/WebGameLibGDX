package com.webgame.game.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MeteorBlastAnimation extends GameAnimation {

    public MeteorBlastAnimation(Texture spriteTexture) {
        super(spriteTexture);
    }

    public MeteorBlastAnimation(Texture spriteTexture, Float animationDuration, Float animationMaxDuration){
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
        int h2 = 50;
        int w2 = 60;
        int l = 5;

        TextureRegion[] frames = new TextureRegion[l];

        for (int i = 0; i < l; i++)
            frames[i] = new TextureRegion(this.spriteTexture, 190 + w2 * (i), 110, w2, h2);

        animation = new Animation<TextureRegion>(animationDuration, frames);

        setAnimation(new Animation<TextureRegion>(animationDuration, frames));

    }
}
