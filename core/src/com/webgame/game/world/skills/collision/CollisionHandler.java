package com.webgame.game.world.skills.collision;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.webgame.game.world.player.Player;
import com.webgame.game.world.skills.Skill;
import com.webgame.game.world.skills.cooldown.SkillContainer;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public class CollisionHandler implements SkillCollision {

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
                skillCollision(enemy, skill);
        }

        List<SkillContainer> containers2 = enemy.getSkillContainers();
        if (containers2.isEmpty())
            return;
        for (SkillContainer skillContainer2 : containers2) {

            if (skillContainer2.isEmpty())
                continue;

            for (Skill<?> skill : skillContainer2)
                skillCollision(player, skill);
        }
    }

    @Override
    public <T extends SkillSprite> void skillCollision(Player player, Skill<T> skill) {
        if (player == null || skill == null || !skill.getSkillState().isActive())
            return;

        if (player.getActorState().getHealthPoints() <= 0) {
            player.getActorState().setHealthPoints(player.getActorState().getMaxHealthPoints());
            return;
        }

        Rectangle area = skill.getSkillState().getArea();
        Circle pShape = player.getPlayerShape();
        boolean isAOE = skill.getSkillState().isAOE();
        boolean isFalling = skill.getSkillState().isFalling();
        boolean isTimed = skill.getSkillState().isTimed();
        boolean isStatic = skill.getSkillState().isStatic();
        boolean isMarked = skill.getSkillState().isMarked();

        if (skill.getSkillState().isBuff() || skill.getSkillState().isHeal()) {
            if (Intersector.overlaps(pShape, area)) {
                if (!skill.getSkillState().isMarked()) {
                    if (skill.getSkillState().isHeal()) {
                        Integer hp = player.getActorState().getHealthPoints();
                        Integer maxHP = player.getActorState().getMaxHealthPoints();
                        Integer resHP = hp + skill.getSkillState().getHealHP().intValue();
                        player.getActorState().setHealthPoints(resHP >= maxHP ? maxHP : resHP);
                    }
                }
                if (!isTimed)
                    skill.getSkillState().setMarked(true);
            }
        } else if ((!isAOE || isAOE && isFalling) && !isStatic) {
            for (T frame : skill.getSkillObjects()) {
                if (!frame.isMarked() && (!isAOE || frame.isStatic()) && Intersector.overlaps(pShape, frame.getBoundingRectangle())) {
                    player.getActorState().setHealthPoints(player.getActorState().getHealthPoints() - skill.getSkillState().getDamage().intValue());
                    frame.setMarked(true);

                }
            }
        } else if (isAOE || !isAOE && isStatic) {
            if (isAOE && !isFalling && Intersector.overlaps(pShape, area)
                    || !isAOE && isStatic && pShape.contains(skill.getTargetPosition())) {
                if (!isMarked)
                    player.getActorState().setHealthPoints(player.getActorState().getHealthPoints() - skill.getSkillState().getDamage().intValue());
                if (!isTimed)
                    skill.getSkillState().setMarked(true);
            }
        }
    }
}