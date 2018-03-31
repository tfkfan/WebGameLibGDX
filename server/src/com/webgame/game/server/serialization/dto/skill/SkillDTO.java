package com.webgame.game.server.serialization.dto.skill;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.skill.Skill;
import com.webgame.game.server.serialization.dto.UpdatableDTO;

public class SkillDTO implements UpdatableDTO<Skill> {
    private Vector2 position;
    private Vector2 target;


    @Override
    public void updateBy(Skill entity) {

    }
}
