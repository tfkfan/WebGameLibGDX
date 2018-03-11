package com.webgame.game.controllers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.skill.AOESkill;
import com.webgame.game.entities.skill.SingleSkill;
import com.webgame.game.entities.skill.Skill;
import com.webgame.game.entities.skill.StaticSkill;
import com.webgame.game.enums.MoveState;
import com.webgame.game.events.AttackEvent;
import com.webgame.game.events.PlayerDamagedEvent;
import com.webgame.game.events.listeners.AttackListener;
import com.webgame.game.events.listeners.PlayerDamagedListener;

import java.util.ArrayList;
import java.util.List;

public class SkillController extends AbstractController implements EventListener {
    private Player player;
    private List<Player> enemies;
    private List<Player> allPlayers;

    private List<EventListener> attackListeners = new ArrayList<>();
    private List<EventListener> playerDamagedListeners = new ArrayList<>();

    public SkillController() {
    }

    public void init(Player player, List<Player> enemies) {
        addAttackListener(new AttackListener());
        addPlayerDamagedListener(new PlayerDamagedListener());

        this.player = player;
        this.enemies = enemies;

        this.allPlayers = new ArrayList<>();
        this.allPlayers.addAll(enemies);
        this.allPlayers.add(player);
        this.addListener(this);
    }

    public void addAttackListener(EventListener listener) {
        attackListeners.add(listener);
    }

    public void addPlayerDamagedListener(EventListener listener) {
        playerDamagedListeners.add(listener);
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
            for (EventListener listener : attackListeners)
                listener.handle(event);
        } else if (event instanceof PlayerDamagedEvent) {
            for (EventListener listener : playerDamagedListeners)
                listener.handle(event);
        }
        return true;
    }


}
