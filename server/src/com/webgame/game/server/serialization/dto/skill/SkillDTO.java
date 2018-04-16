package com.webgame.game.server.serialization.dto.skill;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.entities.skill.Skill;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.MoveState;
import com.webgame.game.enums.SkillKind;
import com.webgame.game.server.serialization.dto.entity.EntityDTO;
import com.webgame.game.server.serialization.dto.UpdatableDTO;

public class SkillDTO extends EntityDTO implements UpdatableDTO<Skill> {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 target;
    private MoveState moveState;
    private EntityState entityState;
    private SkillKind skillType;

    public SkillDTO() {

    }

    public SkillDTO(String id, MoveState moveState, EntityState entityState, SkillKind skillType, Vector2 target, Vector2 position, Vector2 velocity) {
        setId(id);
        setMoveState(moveState);
        setEntityState(entityState);
        setPosition(position);
        setTarget(target);
        setVelocity(velocity);
        setSkillType(skillType);
    }

    public SkillDTO(Skill skill) {
        updateBy(skill);
    }

    @Override
    public void updateBy(Skill entity) {
        setPosition(entity.getPosition());
        setTarget(entity.getTarget());
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public MoveState getMoveState() {
        return moveState;
    }

    public void setMoveState(MoveState moveState) {
        this.moveState = moveState;
    }

    public EntityState getEntityState() {
        return entityState;
    }

    public void setEntityState(EntityState entityState) {
        this.entityState = entityState;
    }

    public SkillKind getSkillType() {
        return skillType;
    }

    public void setSkillType(SkillKind skillType) {
        this.skillType = skillType;
    }
}
