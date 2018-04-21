package com.webgame.client.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class GameAnimation{
    protected Texture spriteTexture;
    protected Animation<TextureRegion> animation;
    protected Float animationDuration;

    public GameAnimation(Texture spriteTexture) {
        setSpriteTexture(spriteTexture);
    }

    public GameAnimation(Texture spriteTexture, Float animationDuration){
        setSpriteTexture(spriteTexture);
        setAnimationDuration(animationDuration);
        initAnimation();
    }

    public GameAnimation(GameAnimation gameAnimation){
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

    public abstract void initAnimation();

    public Float getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(Float animationDuration) {
        this.animationDuration = animationDuration;
    }
}
