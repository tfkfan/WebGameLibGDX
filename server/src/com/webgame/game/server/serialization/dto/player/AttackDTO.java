package com.webgame.game.server.serialization.dto.player;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.server.serialization.dto.DTO;

public class AttackDTO implements DTO {
    Long id;
    Vector2 target;
    
    public  AttackDTO(){

    }
    public AttackDTO(Long id, Vector2 target){
        setId(id);
        setTarget(target);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }
}
