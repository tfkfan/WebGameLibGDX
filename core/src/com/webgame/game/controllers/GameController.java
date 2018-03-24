package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
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
import com.webgame.game.events.listeners.ws.SuccesLoginWSListener;
import com.webgame.game.events.listeners.ws.PlayerWSListener;
import com.webgame.game.events.ws.LoginSuccessEvent;
import com.webgame.game.events.ws.PlayerWSEvent;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;

public class GameController extends AbstractGameController {
    public GameController(OrthographicCamera camera, Viewport viewport) {
        super(camera, viewport);

        addSuccessLoginWSListener(new SuccesLoginWSListener() {
            @Override
            public void customHandle(LoginSuccessEvent event) {
                Gdx.app.postRunnable(() -> {
                    LoginDTO playerDTO = event.getLoginDTO();

                    Gdx.app.log("websocket", "Player created " + playerDTO.getId());

                    Player player = Player.createPlayer(world);

                    player.getAttributes().setName(playerDTO.getName());
                    player.setPosition(playerDTO.getPosition());
                    player.setId(playerDTO.getId());
                    setPlayer(player);
                    getPlayers().put(player.getId(), player);
                });
            }
        });

        addPlayerMoveListener(new PlayerMoveListener() {
            @Override
            public void customHandle(MoveEvent event) {
                Player plr = event.getPlayer();
                Vector2 vec = event.getVector();
                plr.setOldDirectionState(plr.getDirectionState());
                plr.setDirectionState(event.getDirectionState());
                plr.setVelocity(vec);
                plr.applyVelocity();

                PlayerDTO playerDTO = new PlayerDTO();
                playerDTO.updateBy(plr);

                getSocketService().send(playerDTO);
            }
        });

        addAttackListener(new AttackListener() {
                              @Override
                              public void customHandle(AttackEvent event) {
                                  Player plr = event.getPlayer();
                                  Skill currentSkill = plr.getCurrentSkill();
                                  if (currentSkill == null)
                                      return;

                                  Long end = currentSkill.getStart() + currentSkill.getCooldown();
                                  Long currentTime = System.currentTimeMillis();

                                  if (currentTime < end)
                                      return;

                                  plr.clearTimers();
                                  plr.setCurrAnimationState(PlayerAnimationState.ATTACK);

                                  plr.castSkill(event.getTargetVector());
                              }
                          }
        );
        addPlayerDamagedListener(new PlayerDamagedListener() {

            @Override
            public void customHandle(PlayerDamagedEvent event) {
                getPlayerDamaged(event.getPlayer(), event.getDamage());
            }

            protected void getPlayerDamaged(Player target, Integer damage) {
                if (target.getAttributes().getHealthPoints() > 0)
                    target.getAttributes().setHealthPoints(target.getAttributes().getHealthPoints() - damage);
                else
                    target.getAttributes().setHealthPoints(0);
            }
        });
        addPlayerWSListener(new PlayerWSListener() {
            @Override
            public void customHandle(PlayerWSEvent event) {
                synchronized (world) {
                    PlayerDTO playerDTO = event.getPlayerDTO();
                    if (!getPlayers().containsKey(playerDTO.getId())) {
                        Player player = Player.createPlayer(world);
                        player.getAttributes().setName(playerDTO.getName());
                        player.setPosition(playerDTO.getPosition());
                        player.setId(playerDTO.getId());

                        getPlayers().put(player.getId(), player);
                    } else
                        getPlayers().get(playerDTO.getId()).setPosition(playerDTO.getPosition());
                }
            }
        });
    }
}
