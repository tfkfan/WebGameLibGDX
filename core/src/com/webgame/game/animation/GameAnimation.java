package com.webgame.game.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class GameAnimation{
    protected Texture spriteTexture;
    protected Animation<TextureRegion> animation;
    protected Float animationMaxDuration;
    protected Float animationDuration;

    public GameAnimation(Texture spriteTexture) {
        setSpriteTexture(spriteTexture);
    }

    public GameAnimation(Texture spriteTexture, Float animationDuration, Float animationMaxDuration){
        setSpriteTexture(spriteTexture);
        setAnimationDuration(animationDuration);
        setAnimationMaxDuration(animationMaxDuration);
        initAnimation();
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

    public abstract void initAnimation();

    public Float getAnimationMaxDuration() {
        return animationMaxDuration;
    }

    public void setAnimationMaxDuration(Float animationMaxDuration) {
        this.animationMaxDuration = animationMaxDuration;
    }

    public Float getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(Float animationDuration) {
        this.animationDuration = animationDuration;
    }
}
