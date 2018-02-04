package com.webgame.game.world.skills.skillsprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.world.common.GameSprite;

public abstract class SkillSprite extends GameSprite implements Cloneable {
    protected boolean isActive;
    protected boolean isStatic;
    protected boolean isFinalAnimated;
    protected boolean isMarked;

    protected GameAnimation gameAnimation;
    protected TextureRegion standTexture;

    protected final Vector2 distance;

    protected float animationDuration;
    protected float animationMaxDuration;
    protected float animateTimer = 0;

    public SkillSprite() {
        super();
        distance = new Vector2(0, 0);
    }
    public SkillSprite(GameAnimation animation){
        super();
        setGameAnimation(animation);
        distance = new Vector2(0, 0);
    }

    public SkillSprite(TextureRegion standTexture){
        super();
        setStandTexture(new TextureRegion(standTexture));
        distance = new Vector2(0, 0);
    }

    public float getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(float animationDuration) {
        this.animationDuration = animationDuration;
    }

    public float getAnimationMaxDuration() {
        return animationMaxDuration;
    }

    public void setAnimationMaxDuration(float animationMaxDuration) {
        this.animationMaxDuration = animationMaxDuration;
    }

    public float getAnimateTimer() {
        return animateTimer;
    }

    public void setAnimateTimer(float animateTimer) {
        this.animateTimer = animateTimer;
    }

    public boolean isFinalAnimated() {
        return isFinalAnimated;
    }

    public void setFinalAnimated(boolean isFinalAnimated) {
        this.isFinalAnimated = isFinalAnimated;
    }

    public SkillSprite setActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public GameAnimation getGameAnimation() {
        return gameAnimation;
    }

    public void setGameAnimation(GameAnimation gameAnimation) {
        this.gameAnimation = gameAnimation;
    }

    public TextureRegion getStandTexture() {
        return standTexture;
    }

    public void setStandTexture(TextureRegion standTexture) {
        this.standTexture = standTexture;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public SkillSprite setStatic(boolean isStatic) {
        this.isStatic = isStatic;
        return this;
    }

    public Vector2 getDistance() {
        return distance;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean isMarked) {
        this.isMarked = isMarked;
    }

    public void updateDistance() {
        distance.y = 0;
        distance.x = 0;
    }

    @Override
    public void update(float dt) {
        if (!isActive)
            return;

        if (!isStatic) {
            setPosition(getX() + getVelocity().x - xOffset, getY() + getVelocity().y - yOffset);
            distance.x += Math.abs(getVelocity().x);
            distance.y += Math.abs(getVelocity().y);
        }

        super.update(dt);
    }

    public void draw() {
        draw(batch);
    }

    public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
        setSpriteBatch(batch);
        setSpriteTexture(spriteTexture);
    }
}
