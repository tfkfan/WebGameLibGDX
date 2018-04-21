package com.webgame.common.client.server.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.common.client.enums.*;
import com.webgame.common.client.server.dto.SpriteAttributesDTO;

public abstract class Skill extends Entity {
    protected Vector2 target;

    protected Rectangle area;

    protected MoveState moveState;
    protected MarkState markState;
    protected SkillKind skillType;

    protected Integer damage;
    protected Integer heal;

    protected Long cooldown;
    protected Long duration;
    protected Long start;

    protected String spritePath;
    protected String animSpritePath;

    protected float[] standSizes = {FrameSizes.LITTLE_SPHERE.getW(), FrameSizes.LITTLE_SPHERE.getH()};
    protected float[] animSizes = {FrameSizes.ANIMATION.getW(), FrameSizes.ANIMATION.getH()};

    protected SpriteAttributesDTO spriteAttributes;

    public Skill() {

    }

    public void updateBy(Skill skill) {
        setPosition(skill.getPosition());
        setTarget(skill.getTarget());
        setEntityState(skill.getEntityState());
        setMoveState(skill.getMoveState());
        setMarkState(skill.getMarkState());
        setSpritePath(skill.getSpritePath());
        setAnimSpritePath(skill.getAnimSpritePath());
        setSizes(skill.getAnimSizes(),skill.getStandSizes());
        setArea(skill.getArea());
        setMarkState(skill.getMarkState());
        setSkillKind(skill.getSkillType());
        setDamage(skill.getDamage());
        setHeal(skill.getHeal());
        setSkillKind(skill.getSkillType());
        setSpriteAttributes(skill.getSpriteAttributes());
    }

    public abstract <T extends Skill> T createCopy();

    public abstract void cast(Vector2 targetPosition);

    public Circle getShape() {
        return new Circle(getPosition(), getWidth() > getHeight() ? getWidth() / 2 : getHeight() / 2);
    }

    public void setSizes(float[] animSizes, float[] standSizes) {
        setStandSizes(standSizes[0], standSizes[1]);
        setAnimSizes(animSizes[0], animSizes[1]);
    }

    public void setStandSizes(float w, float h) {
        this.setSize(w, h);
        standSizes[0] = w;
        standSizes[1] = h;
    }

    public void setAnimSizes(float w, float h) {
        animSizes[0] = w;
        animSizes[1] = h;
    }

    public SpriteAttributesDTO getSpriteAttributes() {
        return spriteAttributes;
    }

    public void setSpriteAttributes(SpriteAttributesDTO spriteAttributes) {
        this.spriteAttributes = spriteAttributes;
    }

    public float[] getStandSizes() {
        return standSizes;
    }

    public void setStandSizes(float[] standSizes) {
        this.standSizes = standSizes;
    }

    public float[] getAnimSizes() {
        return animSizes;
    }

    public void setAnimSizes(float[] animSizes) {
        this.animSizes = animSizes;
    }

    public String getAnimSpritePath() {
        return animSpritePath;
    }

    public void setAnimSpritePath(String animSpritePath) {
        this.animSpritePath = animSpritePath;
    }


    public String getSpritePath() {
        return spritePath;
    }

    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
    }

    public Rectangle getArea() {
        return area;
    }

    public void setArea(Rectangle area) {
        this.area = area;
    }

    public MarkState getMarkState() {
        return markState;
    }

    public void setMarkState(MarkState markState) {
        this.markState = markState;
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

    public Long getCooldown() {
        return cooldown;
    }

    public void setCooldown(Long cooldown) {
        this.cooldown = cooldown;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public MoveState getMoveState() {
        return moveState;
    }

    public void setMoveState(MoveState moveState) {
        this.moveState = moveState;
    }

    public SkillKind getSkillType() {
        return skillType;
    }

    public void setSkillKind(SkillKind skillType) {
        this.skillType = skillType;
    }
}
