package com.webgame.game.factory;

import com.webgame.game.entities.skill.ClientSkill;
import com.webgame.game.enums.SkillKind;
import com.webgame.game.server.entities.Player;
import com.webgame.game.server.entities.Skill;

public interface ISkillInitiator {
    void initSkill(ClientSkill skill, Player player);
}
