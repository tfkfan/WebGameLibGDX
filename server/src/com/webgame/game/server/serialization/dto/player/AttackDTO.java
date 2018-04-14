package com.webgame.game.server.serialization.dto.player;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.enums.SkillTypeState;
import com.webgame.game.server.serialization.dto.DTO;
import com.webgame.game.server.serialization.dto.entity.EntityDTO;

public final class AttackDTO extends EntityDTO implements DTO {
    Vector2 target;
    SkillTypeState skillType;
    
    public  AttackDTO(){

    }
    public AttackDTO(String id, Vector2 target, SkillTypeState skillType){
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

    public SkillTypeState getSkillType() {
        return skillType;
    }

    public void setSkillType(SkillTypeState skillType) {
        this.skillType = skillType;
    }
}
