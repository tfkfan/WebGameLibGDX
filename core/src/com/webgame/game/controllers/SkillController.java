package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.webgame.game.entities.player.Enemy;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.skill.AOESkill;
import com.webgame.game.entities.skill.SingleSkill;
import com.webgame.game.entities.skill.Skill;
import com.webgame.game.entities.skill.StaticSkill;
import com.webgame.game.enums.MoveState;
import com.webgame.game.enums.PlayerAnimationState;
import com.webgame.game.events.AttackEvent;
import com.webgame.game.events.PlayerDamagedEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SkillController extends AbstractController implements EventListener {
    private Player player;
    private List<Player> enemies;
    private List<Player> allPlayers;

    public SkillController() {
    }

    public void init(Player player, List<Player> enemies) {
        this.player = player;
        this.enemies = enemies;

        this.allPlayers = new ArrayList<>();
        this.allPlayers.addAll(enemies);
        this.allPlayers.add(player);
        this.addListener(this);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        for (Player currentPlayer : allPlayers) {
            List<Skill> skills = currentPlayer.getActiveSkills();
            for (Skill skill : skills) {
                skill.update(dt);

                if (!(skill instanceof AOESkill) && skill.isMarked())
                    continue;

                for (Player anotherPlayer : allPlayers) {
                    if (anotherPlayer == currentPlayer)
                        continue;

                    //handling collision
                    if (skill instanceof AOESkill) {
                        if (Intersector.overlaps(anotherPlayer.getShape(), ((AOESkill) skill).getArea())) {
                            this.fire(new PlayerDamagedEvent(anotherPlayer, skill.getDamage()));
                        }
                    } else {
                        if (Intersector.overlaps(skill.getShape(), anotherPlayer.getShape())) {
                            if (skill instanceof SingleSkill || skill instanceof StaticSkill) {
                                if (skill.getMoveState().equals(MoveState.STATIC)) {
                                    this.fire(new PlayerDamagedEvent(anotherPlayer, skill.getDamage()));
                                    skill.setMarked(true);
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        List<Skill> skills = player.getActiveSkills();
        for (Skill skill : skills)
            skill.draw(batch, parentAlpha);
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof AttackEvent) {
            //cooldown
            Skill currentSkill = player.getCurrentSkill();
            if (currentSkill == null)
                return false;

            Long end = currentSkill.getStart() + currentSkill.getCooldown();
            Long currentTime = System.currentTimeMillis();
            //Gdx.app.log("", "end:" + new Date(end) + " / curr:" + new Date(currentTime));

            if (currentTime < end)
                return false;

            //Casting skill
            if (!player.getCurrAnimationState().equals(PlayerAnimationState.ATTACK)) {
                player.clearTimers();
                player.setCurrAnimationState(PlayerAnimationState.ATTACK);
            }

            player.castSkill(((AttackEvent) event).getTargetVector());
        } else if (event instanceof PlayerDamagedEvent) {
            getPlayerDamaged(((PlayerDamagedEvent) event).getPlayer(), ((PlayerDamagedEvent) event).getDamage());
        }
        return true;
    }

    protected void getPlayerDamaged(Player target, Integer damage) {
        if (target.getAttributes().getHealthPoints() > 0)
            target.getAttributes().setHealthPoints(target.getAttributes().getHealthPoints() - damage);
        else
            target.getAttributes().setHealthPoints(0);
    }

}
