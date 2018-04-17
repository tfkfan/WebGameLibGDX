package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.webgame.game.Configs;
import com.webgame.game.entities.player.ClientPlayer;
import com.webgame.game.entities.skill.ClientSkill;
import com.webgame.game.enums.*;
import com.webgame.game.events.AttackEvent;
import com.webgame.game.events.MoveEvent;
import com.webgame.game.events.PlayerDamagedEvent;
import com.webgame.game.events.ws.LoginSuccessEvent;
import com.webgame.game.events.ws.PlayersWSEvent;
import com.webgame.game.server.serialization.dto.Attack;
import com.webgame.game.server.serialization.dto.Login;
import com.webgame.game.server.entities.Player;
import com.webgame.game.server.entities.Skill;

import java.util.*;

public class GameController extends AbstractGameController {
    public GameController(OrthographicCamera camera, Viewport viewport) {
        super(camera, viewport);

        addPlayerMoveListener(event -> {
            MoveEvent moveEvent = (MoveEvent) event;
            ClientPlayer plr = moveEvent.getClientPlayer();
            Vector2 vec = moveEvent.getVector();
            plr.setOldDirectionState(plr.getDirectionState());
            plr.setDirectionState(moveEvent.getDirectionState());
            plr.setVelocity(vec);
            plr.applyVelocity();
            return true;
        });

        addAttackListener(event -> {
                    AttackEvent attackEvent = (AttackEvent) event;
                    ClientPlayer plr = attackEvent.getClientPlayer();

                    plr.clearTimers();
                    plr.setCurrentAttackState(PlayerAttackState.BATTLE);

                    Gdx.app.log("attack", "attack has been sent");
                    getSocketService().send(new Attack(plr.getId(), attackEvent.getTargetVector(), SkillKind.FALLING_AOE));
                    return true;
                }
        );

        addPlayerDamagedListener(event -> {
            PlayerDamagedEvent playerDamagedEvent = (PlayerDamagedEvent) event;
            ClientPlayer target = playerDamagedEvent.getClientPlayer();
            Integer damage = playerDamagedEvent.getDamage();
            if (target.getHealthPoints() > 0)
                target.setHealthPoints(target.getHealthPoints() - damage);
            else
                target.setHealthPoints(0);
            return true;
        });


        //WEBSOCKET EVENTS
        addSuccessLoginWSListener(event -> {
            if (getClientPlayer() != null)
                return true;
            Gdx.app.postRunnable(() -> {
                LoginSuccessEvent loginSuccessEvent = (LoginSuccessEvent) event;
                Login playerDTO = loginSuccessEvent.getLoginDTO();

                Gdx.app.log("websocket", "ClientPlayer created " + playerDTO.getId());

                ClientPlayer clientPlayer = ClientPlayer.createPlayer(world);

                clientPlayer.setName(playerDTO.getName());
                clientPlayer.setPosition(playerDTO.getPosition());
                clientPlayer.getB2body().setTransform(playerDTO.getPosition(), 0);
                clientPlayer.setId(playerDTO.getId());
                setClientPlayer(clientPlayer);
                getPlayers().put(clientPlayer.getId(), clientPlayer);

            });
            return true;
        });


        addPlayersWSListener(event -> {
            Array<Player> serverPlayers = ((PlayersWSEvent) event).getPlayers();

            for (final Player playerDTO : serverPlayers) {
                final ClientPlayer plr = getPlayers().get(playerDTO.getId());
                if (plr == null) {
                    Gdx.app.postRunnable(() -> {
                        Gdx.app.log("websocket", "Enemy created " + playerDTO.getId());

                        ClientPlayer clientPlayer = ClientPlayer.createPlayer(world);

                        clientPlayer.setName(playerDTO.getName());
                        clientPlayer.setPosition(playerDTO.getPosition());
                        clientPlayer.getB2body().setTransform(playerDTO.getPosition(), 0);
                        clientPlayer.setId(playerDTO.getId());

                        getPlayers().put(clientPlayer.getId(), clientPlayer);

                    });
                } else {
                    plr.updateBy(playerDTO);
                    final Map<String, Skill> skills = playerDTO.getSkills();

                    //checking inactive skills
                    for (Iterator<Map.Entry<String, ClientSkill>> it = plr.getActiveSkills().entrySet().iterator(); it.hasNext(); ) {
                        Map.Entry<String, ClientSkill> skillEntry = it.next();
                        if (skillEntry.getValue().getEntityState().equals(EntityState.INACTIVE)) {
                            it.remove();
                        }
                    }

                    final Map<String, ClientSkill> plrSkills = plr.getActiveSkills();

                    if (skills != null && !skills.isEmpty()) {
                        for (Iterator<Map.Entry<String, Skill>> it = skills.entrySet().iterator(); it.hasNext(); ) {
                            Map.Entry<String, Skill> skillEntry = it.next();
                            try {
                                final Object objId = skillEntry.getKey();
                                final String id = (String) objId;
                                final Skill skillDTO = skillEntry.getValue();
                                if (plrSkills.containsKey(id)) {
                                    ClientSkill clientSkill = plrSkills.get(id);
                                    clientSkill.setPosition(skillDTO.getPosition());
                                    clientSkill.setEntityState(skillDTO.getEntityState());
                                    clientSkill.setMoveState(skillDTO.getMoveState());
                                } else {
                                    Gdx.app.log("WS", "ClientSkill is initialized");

                                    plr.clearTimers();
                                    plr.setCurrentAttackState(PlayerAttackState.BATTLE);

                                    ClientSkill clientSkill = plr.castSkill(skillDTO.getTarget(), skillDTO.getId());
                                    if (clientSkill != null)
                                        clientSkill.setPosition(skillDTO.getPosition());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            return true;
        });
    }

    public void playerLogin(String username, String password) {
        getSocketService().connect();
        getSocketService().send(new Login(username));
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if (getClientPlayer() == null)
            return;

        handleInput();

        getSocketService().send(new Player(getClientPlayer()));

        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);

        for (final ClientPlayer currentClientPlayer : getPlayers().values()) {
            currentClientPlayer.update(dt);

            final Collection<ClientSkill> clientSkills = currentClientPlayer.getActiveSkills().values();
            for (Iterator<ClientSkill> it1 = clientSkills.iterator(); it1.hasNext(); ) {
                final ClientSkill clientSkill = it1.next();
                clientSkill.update(dt);

                //if (!(clientSkill instanceof AOEClientSkill) && clientSkill.isMarked())
                //    continue;

               /* for (ClientPlayer anotherPlayer : getPlayers().values()) {
                    if (anotherPlayer == currentClientPlayer)
                        continue;

                    //handling collision
                    if (clientSkill instanceof AOEClientSkill) {
                        if (Intersector.overlaps(anotherPlayer.getShape(), ((AOEClientSkill) clientSkill).getArea())) {
                            this.fire(new PlayerDamagedEvent(anotherPlayer, clientSkill.getDamage()));
                        }
                    } else {
                        if (Intersector.overlaps(clientSkill.getShape(), anotherPlayer.getShape())) {
                            if (clientSkill instanceof SingleClientSkill || clientSkill instanceof StaticClientSkill) {
                                if (clientSkill.getStatic().equals(MoveState.STATIC)) {
                                    this.fire(new PlayerDamagedEvent(anotherPlayer, clientSkill.getDamage()));
                                    clientSkill.setMarked(true);
                                }
                            }
                        }
                    }
                }*/
            }
        }

        camera.position.x = clientPlayer.getPosition().x;
        camera.position.y = clientPlayer.getPosition().y;
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        world.step(0.01f, 6, 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (clientPlayer == null)
            return;

        worldRenderer.render();

        //NOT REMOVE!
        batch.end();
        batch.begin();

        for (ClientPlayer plr : getPlayers().values()) {
            final Collection<ClientSkill> clientSkills = plr.getActiveSkills().values();
            for (Iterator<ClientSkill> it1 = clientSkills.iterator(); it1.hasNext(); ) {
                final ClientSkill clientSkill = it1.next();
                clientSkill.draw(batch, parentAlpha);
            }

            plr.draw(batch, parentAlpha);
            if (plr.getName() != null)
                font.draw(batch, plr.getName(), plr.getPosition().x - plr.getWidth() / 2, plr.getPosition().y + plr.getHeight() + 5 / Configs.PPM);
        }

        //drawing figures(hp)
        batch.end();

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (ClientPlayer plr : getPlayers().values())
            drawPlayerHealthLine(plr);

        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(clientPlayer.getPosition().x, clientPlayer.getPosition().y, clientPlayer.getRadius(), 50);
        shapeRenderer.end();

        batch.begin();
    }

}
