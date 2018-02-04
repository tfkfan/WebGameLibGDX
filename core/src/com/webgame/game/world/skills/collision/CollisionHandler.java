package com.webgame.game.world.skills.collision;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.webgame.game.world.player.Player;
import com.webgame.game.world.skills.Skill;
import com.webgame.game.world.skills.collision.SkillCollision;
import com.webgame.game.world.skills.cooldown.SkillContainer;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public class CollisionHandler {

    public CollisionHandler() {

    }

    public void collision(Player player, Player enemy) {
        if (enemy == player)
            return;

        List<SkillContainer> containers = player.getSkillContainers();
        if (containers.isEmpty())
            return;

        for (SkillContainer skillContainer : containers) {
            if (skillContainer.isEmpty())
                continue;

            for (Skill<?> skill : skillContainer)
                skill.skillCollision(enemy);
        }

        List<SkillContainer> containers2 = enemy.getSkillContainers();
        if (containers2.isEmpty())
            return;
        for (SkillContainer skillContainer2 : containers2) {

            if (skillContainer2.isEmpty())
                continue;

            for (Skill<?> skill : skillContainer2)
                skill.skillCollision(player);
        }
    }


}