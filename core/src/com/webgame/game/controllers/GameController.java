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
import com.webgame.game.factory.ISkillInitiator;
import com.webgame.game.factory.impl.SkillInitiator;
import com.webgame.game.server.dto.AttackDTO;
import com.webgame.game.server.dto.LoginDTO;
import com.webgame.game.server.entities.Player;
import com.webgame.game.server.entities.Skill;

import java.util.*;

public class GameController extends AbstractGameController {
    private final ISkillInitiator skillInitiator = new SkillInitiator();

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

            if(plr.getCurrentSkill()==null)
                return false;

            plr.clearTimers();
            plr.setCurrentAttackState(PlayerAttackState.BATTLE);

            getSocketService().send(new AttackDTO(plr.getId(), attackEvent.getTargetVector(), plr.getCurrentSkill().getSkillType()));
            return true;
        });

        //WEBSOCKET EVENTS
        addSuccessLoginWSListener(event -> {
            if (getClientPlayer() != null)
                return true;
            Gdx.app.postRunnable(() -> {
                LoginSuccessEvent loginSuccessEvent = (LoginSuccessEvent) event;
                LoginDTO loginDTO = loginSuccessEvent.getLoginDTO();

                ClientPlayer clientPlayer = (ClientPlayer) loginDTO.getPlayer();
                clientPlayer.initPlayer();
                clientPlayer.createObject(world);

                setClientPlayer(clientPlayer);
                getPlayers().put(clientPlayer.getId(), clientPlayer);

                Gdx.app.log("websocket", "ClientPlayer created " + clientPlayer.getId());

            });
            return true;
        });


        addPlayersWSListener(event -> {
            Array<Player> serverPlayers = ((PlayersWSEvent) event).getPlayers();

            for (final Player playerDTO : serverPlayers) {
                if (!getPlayers().containsKey(playerDTO.getId())) {
                    Gdx.app.postRunnable(() -> {
                        ClientPlayer clientPlayer = (ClientPlayer) playerDTO;
                        clientPlayer.initPlayer();
                       // clientPlayer.setSkills(null);

                        clientPlayer.createObject(world);

                        Gdx.app.log("websocket", "Enemy created " + playerDTO.getId());

                        getPlayers().put(clientPlayer.getId(), clientPlayer);

                    });
                } else {
                    ClientPlayer plr = getPlayers().get(playerDTO.getId());
                    plr.updateBy(playerDTO);
                    final Map<String, Skill> skills = playerDTO.getSkills();

                    //checking inactive skills
                    for (Iterator<Map.Entry<String, Skill>> it = plr.getSkills().entrySet().iterator(); it.hasNext(); ) {
                        Map.Entry<String, Skill> skillEntry = it.next();
                        if (skillEntry.getValue().getEntityState().equals(EntityState.INACTIVE)) {
                            it.remove();
                        }
                    }

                    final Map<String, Skill> plrSkills = plr.getSkills();

                    if (skills != null && !skills.isEmpty()) {
                        for (Iterator<Map.Entry<String, Skill>> it = skills.entrySet().iterator(); it.hasNext(); ) {
                            Map.Entry<String, Skill> skillEntry = it.next();
                            try {
                                final Object objId = skillEntry.getKey();
                                final String id = (String) objId;
                                final ClientSkill skillDTO = (ClientSkill)skillEntry.getValue();
                                if (plrSkills.containsKey(id) && ((ClientSkill)plrSkills.get(id)).getAnimations() != null) {
                                    ClientSkill clientSkill = (ClientSkill) plrSkills.get(id);
                                    clientSkill.updateBy(skillDTO);
                                } else {
                                    plr.clearTimers();
                                    plr.setCurrentAttackState(PlayerAttackState.BATTLE);

                                    ClientSkill clientSkill =  skillDTO;
                                    synchronized (skillInitiator) {
                                        Gdx.app.postRunnable(() -> {
                                            clientSkill.init(plr);
                                            clientSkill.updateBy(skillDTO);
                                            skillInitiator.initSkill(clientSkill, clientSkill.getSkillType());
                                            clientSkill.cast(clientSkill.getTarget());
                                            plrSkills.put(clientSkill.getId(), clientSkill);
                                        });
                                    }
                                    Gdx.app.log("WS", "ClientSkill is initialized");
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
        getSocketService().send(new LoginDTO(username, password));
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if (getClientPlayer() == null)
            return;

        handleInput();

        getSocketService().send(getClientPlayer());

        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);

        for (final ClientPlayer currentClientPlayer : getPlayers().values()) {
            currentClientPlayer.update(dt);

            final Collection<ClientSkill> clientSkills = currentClientPlayer.getSkills().values();
            for (Iterator<ClientSkill> it1 = clientSkills.iterator(); it1.hasNext(); ) {
                final ClientSkill clientSkill = it1.next();
                clientSkill.update(dt);
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
            plr.draw(batch, parentAlpha);

            final Collection<ClientSkill> clientSkills = plr.getSkills().values();
            for (Iterator<ClientSkill> it1 = clientSkills.iterator(); it1.hasNext(); ) {
                final ClientSkill clientSkill = it1.next();
                clientSkill.draw(batch, parentAlpha);
            }

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
