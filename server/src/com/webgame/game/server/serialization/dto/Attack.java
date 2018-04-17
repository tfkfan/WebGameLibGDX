package com.webgame.game.server.serialization.dto;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.enums.SkillKind;
import com.webgame.game.server.serialization.dto.DTO;
import com.webgame.game.server.entities.Entity;

public final class Attack implements DTO {
    Vector2 target;
    SkillKind skillType;
    String id;

    
    public Attack(){

    }
    public Attack(String id, Vector2 target, SkillKind skillType){
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
