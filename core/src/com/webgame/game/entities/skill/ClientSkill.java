package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.player.ClientPlayer;
import com.webgame.game.enums.*;
import com.webgame.game.server.entities.Player;
import com.webgame.game.server.entities.Skill;
import com.webgame.game.world.common.IUpdatable;

import java.util.ArrayList;
import java.util.List;

public abstract class ClientSkill extends Skill implements IUpdatable {
    protected transient int animationsNum;
    protected transient float stateTimer;
    protected transient Player player;
    protected transient List<SkillSprite> animations;

    public ClientSkill() {
        super();
    }

    public ClientSkill(ClientSkill clientSkill) {
        super();
        updateBy(clientSkill);
    }

    public void init(Player clientPlayer) {
        clearTimers();
        setPlayer(clientPlayer);
        setStart(0L);
        setCooldown(0L);
        setMarkState(MarkState.UNMARKED);
    }

    public abstract void updateAnimations(float dt);

    public void initAnimations(TextureRegion standTexture, GameAnimation gameAnimation, Integer numFrames,
                                  SkillAnimationState animationState, Boolean looping) {
        List<SkillSprite> animations = new ArrayList<SkillSprite>();
        this.animationsNum = numFrames;
        for (int i = 0; i < numFrames; i++) {
            SkillSprite animation = new SkillSprite(standTexture, gameAnimation, animationState, looping);

            if (standSizes != null && animSizes != null)
                animation.setSizes(animSizes, standSizes);

            initAnimation(animation);
            animations.add(animation);
        }
        setAnimations(animations);
    }

    protected void copyAnimations(List<SkillSprite> animations) {
        if (animations == null || animations.isEmpty())
            return;
        List<SkillSprite> newAnimations = new ArrayList<SkillSprite>();
        for (SkillSprite animation : animations) {
            newAnimations.add(new SkillSprite(animation));
        }
        setAnimations(newAnimations);
    }

    public void initAnimation(SkillSprite animation) {
    }

    @Override
    public void update(float dt) {
        if (getEntityState().equals(EntityState.INACTIVE)) {
            clearTimers();
            return;
        }

        updateAnimations(dt);

        stateTimer += dt;

        if (stateTimer >= 50)
            clearTimers();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (animations == null || animations.isEmpty() || getEntityState().equals(EntityState.INACTIVE))
            return;
        for (SkillSprite animation : animations)
            if (animation.getEntityState().equals(EntityState.ACTIVE))
                animation.draw(batch, parentAlpha);
    }

    public void resetSkill() {
        setEntityState(EntityState.INACTIVE);
        setMoveState(MoveState.MOVING);
        clearTimers();
        for (SkillSprite animation : animations) {
            animation.clearTimers();
            animation.init();
            animation.setEntityState(EntityState.INACTIVE);
        }
    }

    public Circle getShape() {
        return new Circle(getPosition().x, getPosition().y, getWidth() > getHeight() ? getHeight() / 2 : getWidth() / 2);
    }

    public MarkState getMarkState() {
        return markState;
    }

    public void setMarkState(MarkState markState) {
        this.markState = markState;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public List<SkillSprite> getAnimations() {
        return animations;
    }

    public void setAnimations(List<SkillSprite> animations) {
        this.animations = animations;
    }

    public Long getCooldown() {
        return cooldown;
    }

    public void setCooldown(Long cooldown) {
        this.cooldown = cooldown;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public void clearTimers() {
        stateTimer = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public float getStateTimer() {
        return stateTimer;
    }

    public void setStateTimer(float stateTimer) {
        this.stateTimer = stateTimer;
    }
}
