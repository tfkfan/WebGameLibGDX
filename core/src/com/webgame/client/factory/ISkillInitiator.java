package com.webgame.client.factory;

import com.webgame.client.entities.skill.ClientSkill;
import com.webgame.server.entities.Player;

public interface ISkillInitiator {
    void initSkill(ClientSkill skill, Player player);
}
