package com.webgame.game.factory;

import com.webgame.game.entities.skill.ClientSkill;
import com.webgame.game.enums.SkillKind;

public interface ISkillInitiator {
    void initSkill(ClientSkill skill, SkillKind skillType);
}
