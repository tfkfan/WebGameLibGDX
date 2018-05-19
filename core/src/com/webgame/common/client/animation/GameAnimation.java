package com.webgame.common.client.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAnimation {
    protected Texture spriteTexture;
    protected Animation<TextureRegion> animation;
    protected Float animationDuration;

    public GameAnimation(Texture spriteTexture) {
        setSpriteTexture(spriteTexture);
    }

    public GameAnimation(Texture spriteTexture, Float animationDuration, int width, int height, int xIterations, int yIterations, int xOffset, int yOffset) {
        setSpriteTexture(spriteTexture);
        setAnimationDuration(animationDuration);
        initAnimation(width, height, xIterations, yIterations, xOffset, yOffset);
    }

    public GameAnimation(GameAnimation gameAnimation) {
        setSpriteTexture(gameAnimation.getSpriteTexture());
        setAnimationDuration(gameAnimation.getAnimationDuration());
        setAnimation(gameAnimation.getAnimation());
    }

    public Texture getSpriteTexture() {
        return spriteTexture;
    }

    public void setSpriteTexture(Texture spriteTexture) {
        this.spriteTexture = spriteTexture;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public void initAnimation(int width, int height, int xIterations, int yIterations, int xOffset, int yOffset) {
        TextureRegion[] animationFrames = new TextureRegion[xIterations * yIterations];

        int k = 0;
        for (int i = 0; i < yIterations; i++) {
            for (int j = 0; j < xIterations; j++, k++)
                animationFrames[k] = new TextureRegion(getSpriteTexture(), xOffset + width * j, yOffset + height * i, width, height);
        }

        setAnimation(new Animation<>(getAnimationDuration(), animationFrames));
    }

    public Float getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(Float animationDuration) {
        this.animationDuration = animationDuration;
    }
}
