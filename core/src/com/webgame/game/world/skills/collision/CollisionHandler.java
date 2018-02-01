package com.webgame.game.world.skills.collision;

import java.util.List;

import com.webgame.game.world.player.Player;
import com.webgame.game.world.skills.Skill;
import com.webgame.game.world.skills.cooldown.SkillContainer;

public class CollisionHandler {

	public CollisionHandler() {

	}

	public void collision(Player player, Player enemy) {
		/*
		if (enemy == player)
			return;

		List<SkillContainer> containers = player.getSkillContainers();
		if (containers.isEmpty())
			return;
		
		for (SkillContainer skillContainer : containers) {
			if (skillContainer.isEmpty())
				continue;

			for (Skill<?> skill : skillContainer) {
				if (!skill.isActive())
					continue;
				skill.skillCollision(enemy);
			}
		}

		List<SkillContainer> containers2 = enemy.getSkillContainers();
		if (containers2.isEmpty())
			return;
		for (SkillContainer skillContainer2 : containers2) {

			if (skillContainer2.isEmpty())
				continue;

			for (Skill<?> skill : skillContainer2) {
				if (!skill.isActive())
					continue;
				skill.skillCollision(player);
			}
		}
		*/
	}
}