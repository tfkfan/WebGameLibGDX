package com.webgame.game.server.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.entities.player.ClientPlayer;
import com.webgame.game.enums.*;
import com.webgame.game.server.entities.Entity;

import static com.webgame.game.Configs.PPM;

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

    public Skill() {

    }

    public Skill(String id, MoveState moveState, EntityState entityState, SkillKind skillType, Vector2 target, Vector2 position, Vector2 velocity) {
        setId(id);
        setMoveState(moveState);
        setEntityState(entityState);
        setPosition(position);
        setTarget(target);
        setVelocity(velocity);
        setSkillType(skillType);
        //TODO remove
        setDamage(100);
        setArea(new Rectangle(0, 0, 100 / PPM, 100 / PPM));
    }

    public void updateBy(Skill skill) {
        setPosition(skill.getPosition());
        setEntityState(skill.getEntityState());
        setMoveState(skill.getMoveState());
        setMarkState(skill.getMarkState());
    }

    public abstract <T extends Skill> T createCopy();

    public void cast(Vector2 targetPosition) {
        setTarget(targetPosition);
        setEntityState(EntityState.ACTIVE);
        setMarkState(MarkState.UNMARKED);

        if (getArea() == null)
            return;

        Vector2 newPos = new Vector2();
        newPos.x = targetPosition.x - getArea().getWidth() / 2;
        newPos.y = targetPosition.y - getArea().getHeight() / 2;
        getArea().setPosition(newPos);
    }

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

    public void setSkillType(SkillKind skillType) {
        this.skillType = skillType;
    }
}
