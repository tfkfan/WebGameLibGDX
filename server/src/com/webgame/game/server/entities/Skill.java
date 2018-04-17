package com.webgame.game.server.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.MarkState;
import com.webgame.game.enums.MoveState;
import com.webgame.game.enums.SkillKind;
import com.webgame.game.server.entities.Entity;

public class Skill extends Entity {
    private Vector2 target;

    private MoveState moveState;
    protected MarkState markState;
    private SkillKind skillType;

    protected Integer damage;
    protected Integer heal;

    protected Long cooldown;
    protected Long duration;
    protected Long start;

    public Skill() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }

    public static Skill createSkill(String id, MoveState moveState, EntityState entityState, SkillKind skillType, Vector2 target, Vector2 position, Vector2 velocity){
        return new Skill(id, moveState, entityState, skillType, target, position, velocity);
    }

    public Skill(String id, MoveState moveState, EntityState entityState, SkillKind skillType, Vector2 target, Vector2 position, Vector2 velocity) {
        setId(id);
        setMoveState(moveState);
        setEntityState(entityState);
        setPosition(position);
        setTarget(target);
        setVelocity(velocity);
        setSkillType(skillType);
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
