package com.webgame.game.server.factory;

import com.webgame.game.enums.SkillKind;
import com.webgame.game.server.entities.Skill;

public interface ISkillFactory {
    Skill createSkill(SkillKind skillKind);
}
