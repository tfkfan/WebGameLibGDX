package com.webgame.game.world.skills.collision;

import com.webgame.game.entities.player.Player;

public class CollisionHandler {

    public CollisionHandler() {

    }

    public void collision(Player player, Player enemy) {
        if (enemy == player)
            return;

        /*
        List<SkillContainer> containers = player.getSkillContainers();
        if (containers.isEmpty())
            return;

        for (SkillContainer skillContainer : containers) {
            if (skillContainer.isEmpty())
                continue;

            for (SkillOrig<?> skill : skillContainer)
                skill.skillCollision(enemy);
        }

        List<SkillContainer> containers2 = enemy.getSkillContainers();
        if (containers2.isEmpty())
            return;
        for (SkillContainer skillContainer2 : containers2) {

            if (skillContainer2.isEmpty())
                continue;

            for (SkillOrig<?> skill : skillContainer2)
                skill.skillCollision(player);
        }
        */
    }


}