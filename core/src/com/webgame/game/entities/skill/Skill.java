package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.Configs;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.AnimatedEntity;
import com.webgame.game.entities.Entity;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.*;
import com.webgame.game.server.serialization.dto.skill.SkillDTO;
import com.webgame.game.world.common.IDTOUpdatable;
import com.webgame.game.world.common.IUpdatable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static com.webgame.game.Configs.PPM;

public abstract class Skill extends Entity implements IUpdatable {
    protected int animationsNum;

    protected Long level;
    protected String title;

    protected Integer damage;
    protected Integer heal;

    protected float stateTimer;

    protected DirectionState directionState;
    protected MoveState moveState;
    protected MarkState markState;
    protected SkillTypeState skillType;

    protected Vector2 target;

    protected Player player;
    protected List<SkillSprite> animations;

    protected Long cooldown;
    protected Long duration;
    protected Long start;

    public Skill() {

    }

    public Skill(Player player) {
        super();
        init(player);
    }

    public Skill(Player player, TextureRegion standTexture, GameAnimation gameAnimation) {
        super();
        init(player);
        initAnimations(standTexture, gameAnimation, 1, SkillAnimationState.FULL_ANIMATION, false, null, null);
    }

    public Skill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, float standSizeX, float standSizeY, float animSizeX, float animSizeY) {
        super();
        init(player);

        float[] standSizes = {standSizeX, standSizeY};
        float[] animSizes = {animSizeX, animSizeY};

        initAnimations(standTexture, gameAnimation, 1, SkillAnimationState.FULL_ANIMATION, false, standSizes, animSizes);
    }

    public Skill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, float[] standSizes, float[] animSizes) {
        super();
        init(player);

        initAnimations(standTexture, gameAnimation, 1, SkillAnimationState.FULL_ANIMATION, false, standSizes, animSizes);
    }

    public Skill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, Boolean looping) {
        super();
        init(player);
        initAnimations(standTexture, gameAnimation, 1, SkillAnimationState.FULL_ANIMATION, looping, null, null);
    }

    public Skill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState) {
        super();
        init(player);
        initAnimations(standTexture, gameAnimation, 1, animationState, false, null, null);
    }

    public Skill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState, Boolean looping) {
        super();
        init(player);
        initAnimations(standTexture, gameAnimation, 1, animationState, looping, null, null);
    }

    public Skill(Player player, TextureRegion standTexture, GameAnimation gameAnimation,
                 Integer numFrames, SkillAnimationState animationState, Boolean looping, float[] standSizes, float[] animSizes) {
        super();
        init(player);
        initAnimations(standTexture, gameAnimation, numFrames, animationState, looping, standSizes, animSizes);
    }

    public Skill(Skill skill) {
        super();
        init(skill.getPlayer());
        copy(skill);
        copyAnimations(skill.getAnimations());
    }

    protected void init(Player player) {
        setTitle("Skill");
        setDamage(0);
        setHeal(0);
        clearTimers();
        setEntityState(EntityState.INACTIVE);
        setMoveState(MoveState.MOVING);
        setPlayer(player);
        setStart(0L);
        setCooldown(0L);
        setMarkState(MarkState.UNMARKED);
    }

    public void cast(Vector2 targetPosition) {
        resetSkill();
        setTarget(targetPosition);
        setEntityState(EntityState.ACTIVE);
        setPosition(new Vector2(player.getPosition()));
    }

    public abstract Skill createCopy();


    public abstract void updateAnimations(float dt);

    protected void initAnimations(TextureRegion standTexture, GameAnimation gameAnimation, Integer numFrames,
                                  SkillAnimationState animationState, Boolean looping, float[] standSizes, float[] animSizes) {
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

    protected void copy(Skill skill) {
        this.setDamage(skill.getDamage());
        this.setHeal(skill.getHeal());
        this.setSkillType(skill.getSkillType());
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

    public MoveState getMoveState() {
        return moveState;
    }

    public void setMoveState(MoveState moveState) {
        this.moveState = moveState;
    }

    public SkillTypeState getSkillType() {
        return skillType;
    }

    public void setSkillType(SkillTypeState skillType) {
        this.skillType = skillType;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getHeal() {
        return heal;
    }

    public void setHeal(Integer heal) {
        this.heal = heal;
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public void setStateTimer(float stateTimer) {
        this.stateTimer = stateTimer;
    }

    public DirectionState getDirectionState() {
        return directionState;
    }

    public void setDirectionState(DirectionState directionState) {
        this.directionState = directionState;
    }


}
