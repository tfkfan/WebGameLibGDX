package com.webgame.game.animation.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.GameAnimation;

public class IceBlastAnimation extends GameAnimation {

    public IceBlastAnimation(Texture spriteTexture) {
        super(spriteTexture);
    }

    public IceBlastAnimation(Texture spriteTexture, Float animationDuration, Float animationMaxDuration){
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
        int h = 130;
        int w = 250;
        int l = 4;
        int l2 = 3;
        TextureRegion[] animationFrames = new TextureRegion[l*l2];


        for (int k = 0, i = 0; i < l; i++) {
            for(int j = 0; j < l2; j++, k++)
                animationFrames[k] = new TextureRegion(spriteTexture, w * j, h*i, w, h);
        }
      //  animationFrames[0] = new TextureRegion(spriteTexture, 0 , 0, w, h);
        setAnimation(new Animation<TextureRegion>(animationDuration, animationFrames));
    }
}
