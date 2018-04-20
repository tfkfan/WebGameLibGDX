package com.webgame.game.animation.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.GameAnimation;

public class FireBlastAnimation2 extends GameAnimation {

    public FireBlastAnimation2(Texture spriteTexture){
        super(spriteTexture,  0.05f);
    }

    @Override
    public void initAnimation() {
        int h = 130;
        int w = 255;
        int l = 4;
        int l2 = 3;
        TextureRegion[] animationFrames = new TextureRegion[l*l2];


        for (int k = 0, i = 0; i < l; i++) {
            for(int j = 0; j < l2; j++, k++)
                animationFrames[k] = new TextureRegion(getSpriteTexture(), w * j, h*i, w, h);
        }
      //  animationFrames[0] = new TextureRegion(spriteTexture, 0 , 0, w, h);
        setAnimation(new Animation<TextureRegion>(getAnimationDuration(), animationFrames));
    }
}
