package com.webgame.server_app.factory;

import com.webgame.common.client.enums.SkillKind;
import com.webgame.common.server.entities.Skill;

public interface ISkillFactory {
    Skill createSkill(SkillKind skillKind);
}
