package com.webgame.game.server.serialization.dto.skill;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.skill.Skill;
import com.webgame.game.server.serialization.dto.UpdatableDTO;

public class SkillDTO implements UpdatableDTO<Skill> {
    private Vector2 position;
    private Vector2 target;
    private Long id;


    public SkillDTO() {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
