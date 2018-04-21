package com.webgame.common.client.server.factory;

import com.webgame.common.client.enums.SkillKind;
import com.webgame.common.client.server.entities.Skill;

public interface ISkillFactory {
    Skill createSkill(SkillKind skillKind);
}
