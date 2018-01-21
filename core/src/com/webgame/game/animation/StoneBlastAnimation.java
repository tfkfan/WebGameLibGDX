package com.webgame.game.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StoneBlastAnimation extends GameAnimation {

    public StoneBlastAnimation(Texture spriteTexture) {
        super(spriteTexture);
    }

    public StoneBlastAnimation(Texture spriteTexture, Float animationDuration, Float animationMaxDuration){
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
        int w = 40;
        int l = 3;

        TextureRegion[] animationFrames = new TextureRegion[l];

        // ��������
        for (int i = 0; i < l; i++)
            animationFrames[i] = new TextureRegion(spriteTexture, -5 + w * (i + 1), 80, w, h);

        setAnimation(new Animation<TextureRegion>(animationDuration, animationFrames));
    }
}
