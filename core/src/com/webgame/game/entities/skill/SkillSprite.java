package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.Configs;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.AnimatedEntity;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.MoveState;
import com.webgame.game.enums.SkillAnimationState;

public class SkillSprite extends AnimatedEntity {
    protected float stateTimer;

    protected TextureRegion standTexture;
    protected GameAnimation animation;
    protected SkillAnimationState animationState;
    protected MoveState moveState;
    protected boolean looping;

    private Vector2 distance;

    public SkillSprite() {
        init();
    }

    public SkillSprite(TextureRegion standTexture, GameAnimation animation) {
        setStandTexture(standTexture);
        setAnimation(animation);
        init();
    }

    public SkillSprite(TextureRegion standTexture, GameAnimation animation,  SkillAnimationState animationState) {
        setStandTexture(standTexture);
        setAnimation(animation);
        setAnimationState(animationState);
        init();
    }

    public SkillSprite(TextureRegion standTexture, GameAnimation animation,  SkillAnimationState animationState, Boolean looping) {
        setStandTexture(standTexture);
        setAnimation(animation);
        setAnimationState(animationState);
        setLooping(looping);
        init();
    }

    public void init() {
        setAnimationState(SkillAnimationState.FULL_ANIMATION);
        setEntityState(EntityState.INACTIVE);
        setMoveState(MoveState.STATIC);
        setLooping(false);
        stateTimer = 0;
        distance = new Vector2(0, 0);
        //TODO change size depending from standTexture/animation size
        setSize(20 / Configs.PPM, 30 / Configs.PPM);
    }

    @Override
    public TextureRegion getFrame() {
        TextureRegion region = null;
        if ((SkillAnimationState.ANIMATION_ONLY.equals(getAnimationState()) ||
                (SkillAnimationState.FULL_ANIMATION.equals(getAnimationState()))) && getMoveState().equals(MoveState.STATIC))
            region = animation.getAnimation().getKeyFrame(stateTimer, isLooping());
        else if (SkillAnimationState.STAND_TEXTURE.equals(getAnimationState()) ||
                (SkillAnimationState.FULL_ANIMATION.equals(getAnimationState())))
            region = standTexture;

        return region;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame = getFrame();
        if (getEntityState().equals(EntityState.ACTIVE))
            batch.draw(frame, position.x - getXOffset() - getWidth()/2, position.y - getYOffset() - getHeight()/2, getWidth(), getHeight());
    }

    public void update(float dt) {
        if (getMoveState().equals(MoveState.MOVING)) {
            setPosition(getPosition().x + getVelocity().x - xOffset, getPosition().y + getVelocity().y - yOffset);
            distance.x += Math.abs(getVelocity().x);
            distance.y += Math.abs(getVelocity().y);
        }
    }

    public void clearTimers(){
        stateTimer = 0;
    }
    public boolean isAnimationFinished(){
        return animation.getAnimation().isAnimationFinished(stateTimer);
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public void setStateTimer(float stateTimer) {
        this.stateTimer = stateTimer;
    }

    public Vector2 getDistance() {
        return distance;
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

    public TextureRegion getStandTexture() {
        return standTexture;
    }

    public void setStandTexture(TextureRegion standTexture) {
        this.standTexture = standTexture;
    }

    public GameAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(GameAnimation animation) {
        this.animation = animation;
    }

    public MoveState getMoveState() {
        return moveState;
    }

    public void setMoveState(MoveState moveState) {
        this.moveState = moveState;
    }

}
