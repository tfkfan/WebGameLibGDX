package com.webgame.common.client.server.dto;

import com.badlogic.gdx.math.Vector2;
import com.webgame.common.client.enums.SkillKind;

public final class AttackDTO implements DTO {
    Vector2 target;
    SkillKind skillType;
    String id;

    
    public AttackDTO(){

    }
    public AttackDTO(String id, Vector2 target, SkillKind skillType){
        setId(id);
        setTarget(target);
        setSkillType(skillType);
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public SkillKind getSkillType() {
        return skillType;
    }

    public void setSkillType(SkillKind skillType) {
        this.skillType = skillType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
