package com.webgame.client.server.factory;

import com.webgame.client.enums.SkillKind;
import com.webgame.client.server.entities.Skill;

public interface ISkillFactory {
    Skill createSkill(SkillKind skillKind);
}
