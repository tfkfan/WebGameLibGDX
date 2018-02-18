package com.webgame.game.animation.skill;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.entities.AnimatedEntity;
import com.webgame.game.enums.SkillAnimationState;

public class SkillAnimation extends AnimatedEntity{
    protected float stateTimer;

    protected TextureRegion standTexture;
    protected Animation<TextureRegion> animation;
    protected SkillAnimationState animationState;
    protected boolean finalAnimated;
    protected boolean looping;

    public SkillAnimation(){
        init();
    }

    public SkillAnimation(TextureRegion standTexture, Animation<TextureRegion> animation){
        setStandTexture(standTexture);
        setAnimation(animation);
        init();
    }

    public void init(){
        setAnimationState(SkillAnimationState.FULL_ANIMATION);
        setFinalAnimated(false);
        setLooping(false);
        stateTimer = 0;
    }

    @Override
    public TextureRegion getFrame(){
        TextureRegion region = null;
        if(SkillAnimationState.ANIMATION_ONLY.equals(getAnimationState()) ||
                (SkillAnimationState.FULL_ANIMATION.equals(getAnimationState()) && isFinalAnimated()))
            region = animation.getKeyFrame(stateTimer, isLooping());
        else if(SkillAnimationState.STAND_TEXTURE.equals(getAnimationState()) ||
                (SkillAnimationState.FULL_ANIMATION.equals(getAnimationState()) && !isFinalAnimated()))
            region = standTexture;

        return region;
    }

    //TODO change size depending from standTexture/animation size
    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(getFrame(), position.x - getXOffset(), position.y - getYOffset(), getWidth(), getHeight());
    }

    public void update(float dt){
        stateTimer += dt;
    }

    public boolean isLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public SkillAnimationState getAnimationState() {
        return animationState;
    }

    public void setAnimationState(SkillAnimationState animationState) {
        this.animationState = animationState;
    }

    public boolean isFinalAnimated() {
        return finalAnimated;
    }

    public void setFinalAnimated(boolean finalAnimated) {
        this.finalAnimated = finalAnimated;
    }

    public TextureRegion getStandTexture() {
        return standTexture;
    }

    public void setStandTexture(TextureRegion standTexture) {
        this.standTexture = standTexture;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

}
