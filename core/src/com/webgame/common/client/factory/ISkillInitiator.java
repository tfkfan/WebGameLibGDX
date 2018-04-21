package com.webgame.common.client.factory;

import com.webgame.common.client.entities.skill.ClientSkill;
import com.webgame.common.server.entities.Player;

public interface ISkillInitiator {
    void initSkill(ClientSkill skill, Player player);
}
