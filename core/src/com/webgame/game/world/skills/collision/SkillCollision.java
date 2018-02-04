package com.webgame.game.world.skills.collision;

import com.webgame.game.world.player.Player;
import com.webgame.game.world.skills.Skill;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public interface SkillCollision {
    public <T extends SkillSprite>  void skillCollision(Player player, Skill<T>  skill);
}
