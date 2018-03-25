package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.skill.Skill;
import com.webgame.game.enums.PlayerAnimationState;
import com.webgame.game.events.AttackEvent;
import com.webgame.game.events.MoveEvent;
import com.webgame.game.events.PlayerDamagedEvent;
import com.webgame.game.events.listeners.AttackListener;
import com.webgame.game.events.listeners.PlayerDamagedListener;
import com.webgame.game.events.listeners.PlayerMoveListener;
import com.webgame.game.events.listeners.ws.EnemyWSListener;
import com.webgame.game.events.listeners.ws.SuccesLoginWSListener;
import com.webgame.game.events.listeners.ws.PlayerWSListener;
import com.webgame.game.events.ws.EnemyWSEvent;
import com.webgame.game.events.ws.LoginSuccessEvent;
import com.webgame.game.events.ws.PlayerWSEvent;
import com.webgame.game.server.serialization.dto.player.EnemyDTO;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;

public class GameController extends AbstractGameController {
    public GameController(OrthographicCamera camera, Viewport viewport) {
        super(camera, viewport);

        addSuccessLoginWSListener(event -> {
            Gdx.app.postRunnable(() -> {
                LoginSuccessEvent loginSuccessEvent = (LoginSuccessEvent) event;
                LoginDTO playerDTO = loginSuccessEvent.getLoginDTO();

                Gdx.app.log("websocket", "Player created " + playerDTO.getId());

                Player player = Player.createPlayer(world);

                player.getAttributes().setName(playerDTO.getName());
                player.setPosition(playerDTO.getPosition());
                player.setId(playerDTO.getId());
                setPlayer(player);
                getPlayers().put(player.getId(), player);

            });
            return true;
        });

        addPlayerMoveListener(event -> {
            MoveEvent moveEvent = (MoveEvent) event;
            Player plr = moveEvent.getPlayer();
            Vector2 vec = moveEvent.getVector();
            plr.setOldDirectionState(plr.getDirectionState());
            plr.setDirectionState(moveEvent.getDirectionState());
            plr.setVelocity(vec);
            plr.applyVelocity();

            PlayerDTO playerDTO = new PlayerDTO();
            playerDTO.updateBy(plr);

            getSocketService().send(playerDTO);
            return true;
        });

        addAttackListener(event -> {
                    AttackEvent attackEvent = (AttackEvent) event;
                    Player plr = attackEvent.getPlayer();
                    Skill currentSkill = plr.getCurrentSkill();
                    if (currentSkill == null)
                        return false;

                    Long end = currentSkill.getStart() + currentSkill.getCooldown();
                    Long currentTime = System.currentTimeMillis();

                    if (currentTime < end)
                        return false;

                    plr.clearTimers();
                    plr.setCurrAnimationState(PlayerAnimationState.ATTACK);

                    plr.castSkill(attackEvent.getTargetVector());
                    return true;
                }
        );
        addPlayerDamagedListener(event -> {
            PlayerDamagedEvent playerDamagedEvent = (PlayerDamagedEvent) event;
            Player target = playerDamagedEvent.getPlayer();
            Integer damage = playerDamagedEvent.getDamage();
            if (target.getAttributes().getHealthPoints() > 0)
                target.getAttributes().setHealthPoints(target.getAttributes().getHealthPoints() - damage);
            else
                target.getAttributes().setHealthPoints(0);
            return true;
        });

        addPlayerWSListener(event -> {
            synchronized (world) {
                PlayerWSEvent playerWSEvent = (PlayerWSEvent) event;
                PlayerDTO playerDTO = playerWSEvent.getPlayerDTO();
                Player player = getPlayer();
                player.setPosition(playerDTO.getPosition());
                return true;
            }
        });
        addEnemyWSListener(event -> {
            Gdx.app.postRunnable(() -> {
                synchronized (world) {
                    EnemyWSEvent enemyWSEvent = (EnemyWSEvent) event;
                    EnemyDTO enemyDTO = enemyWSEvent.getEnemyDTO();
                    if (!getPlayers().containsKey(enemyDTO.getId())) {
                        Player player = Player.createPlayer(world);
                        player.getAttributes().setName(enemyDTO.getName());
                        player.setPosition(enemyDTO.getPosition());
                        player.setId(enemyDTO.getId());

                        getPlayers().put(player.getId(), player);
                    } else {
                        Gdx.app.log("WS", "Id: " + enemyDTO.getId() + " currPlayer: " + player.getId());
                        getPlayers().get(enemyDTO.getId()).setPosition(enemyDTO.getPosition());
                    }

                }
            });
            return true;
        });
    }
}
