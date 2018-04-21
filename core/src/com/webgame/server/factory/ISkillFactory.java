package com.webgame.server.factory;

import com.webgame.client.enums.SkillKind;
import com.webgame.server.entities.Skill;

public interface ISkillFactory {
    Skill createSkill(SkillKind skillKind);
}
