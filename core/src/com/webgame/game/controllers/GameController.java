package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.webgame.game.Configs;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.skill.AOESkill;
import com.webgame.game.entities.skill.SingleSkill;
import com.webgame.game.entities.skill.Skill;
import com.webgame.game.entities.skill.StaticSkill;
import com.webgame.game.enums.MoveState;
import com.webgame.game.enums.PlayerAttackState;
import com.webgame.game.enums.PlayerMoveState;
import com.webgame.game.events.AttackEvent;
import com.webgame.game.events.MoveEvent;
import com.webgame.game.events.PlayerDamagedEvent;
import com.webgame.game.events.listeners.ws.AttackWSListener;
import com.webgame.game.events.ws.AttackWSEvent;
import com.webgame.game.events.ws.LoginSuccessEvent;
import com.webgame.game.events.ws.PlayersWSEvent;
import com.webgame.game.server.serialization.dto.player.AttackDTO;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;

import java.util.List;

public class GameController extends AbstractGameController {
    Long timerId;

    public GameController(OrthographicCamera camera, Viewport viewport) {
        super(camera, viewport);

        addPlayerMoveListener(event -> {
            MoveEvent moveEvent = (MoveEvent) event;
            Player plr = moveEvent.getPlayer();
            Vector2 vec = moveEvent.getVector();
            plr.setOldDirectionState(plr.getDirectionState());
            plr.setDirectionState(moveEvent.getDirectionState());
            plr.setVelocity(vec);
            plr.applyVelocity();

            //getSocketService().send(new PlayerDTO(getPlayer()));
            return true;
        });

        addAttackListener(event -> {
                    AttackEvent attackEvent = (AttackEvent) event;
                    Player plr = attackEvent.getPlayer();

                    plr.clearTimers();
                    plr.setCurrentAttackState(PlayerAttackState.BATTLE);

                    if(plr.castSkill(attackEvent.getTargetVector())){
                        Gdx.app.log("attack", "attack has been sent");
                        getSocketService().send(new AttackDTO(plr.getId(), attackEvent.getTargetVector()));
                    }
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


        //WEBSOCKET EVENTS
        addSuccessLoginWSListener(event -> {
            if (getPlayer() != null)
                return true;
            Gdx.app.postRunnable(() -> {
                LoginSuccessEvent loginSuccessEvent = (LoginSuccessEvent) event;
                LoginDTO playerDTO = loginSuccessEvent.getLoginDTO();

                Gdx.app.log("websocket", "Player created " + playerDTO.getId());

                Player player = Player.createPlayer(world);

                player.getAttributes().setName(playerDTO.getName());
                player.setPosition(playerDTO.getPosition());
                player.getB2body().setTransform(playerDTO.getPosition(), 0);
                player.setId(playerDTO.getId());
                setPlayer(player);
                getPlayers().put(player.getId(), player);

            });
            return true;
        });


        addPlayersWSListener(event -> {
            Array<PlayerDTO> serverPlayers = ((PlayersWSEvent) event).getPlayers();

            for (final PlayerDTO playerDTO : serverPlayers) {
                final Player plr = getPlayers().get(playerDTO.getId());
                if (plr == null) {
                    Gdx.app.postRunnable(() -> {
                        Gdx.app.log("websocket", "Enemy created " + playerDTO.getId());

                        Player player = Player.createPlayer(world);

                        player.getAttributes().setName(playerDTO.getName());
                        player.setPosition(playerDTO.getPosition());
                        player.getB2body().setTransform(playerDTO.getPosition(), 0);
                        player.setId(playerDTO.getId());

                        getPlayers().put(player.getId(), player);

                    });
                } else if (!plr.equals(getPlayer())) {
                    plr.updateBy(playerDTO);
                }
            }
            return true;
        });

        addAttackWSListener(event -> {
            AttackDTO dto = ((AttackWSEvent)event).getAttackDTO();
            Long id = dto.getId();
            Player plr = getPlayers().get(id);
            if(plr == null){
                Gdx.app.log("WS", "Player " + id + " doesn't exist");
                return true;
            }
            plr.clearTimers();
            plr.setCurrentAttackState(PlayerAttackState.BATTLE);

            return plr.castSkill(dto.getTarget());
        });

    }

    public void playerLogin(String username, String password) {
        getSocketService().connect();
        getSocketService().send(new LoginDTO(username));
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if (getPlayer() == null)
            return;

        handleInput();

        getSocketService().send(new PlayerDTO(getPlayer()));

        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);

        for (final Player currentPlayer : getPlayers().values()) {
            currentPlayer.update(dt);
            List<Skill> skills = currentPlayer.getActiveSkills();

            for (Skill skill : skills) {
                skill.update(dt);

                if (!(skill instanceof AOESkill) && skill.isMarked())
                    continue;

                for (Player anotherPlayer : getPlayers().values()) {
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

        camera.position.x = player.getPosition().x;
        camera.position.y = player.getPosition().y;
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        world.step(0.01f, 6, 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (player == null)
            return;

        worldRenderer.render();

        //NOT REMOVE!
        batch.end();
        batch.begin();

        for (Player plr : getPlayers().values()) {
            List<Skill> skills = plr.getActiveSkills();
            for (Skill skill : skills)
                skill.draw(batch, parentAlpha);

            plr.draw(batch, parentAlpha);
            if (plr.getAttributes().getName() != null)
                font.draw(batch, plr.getAttributes().getName(), plr.getPosition().x - plr.getWidth() / 2, plr.getPosition().y + plr.getHeight() + 5 / Configs.PPM);
        }

        //drawing figures(hp)
        batch.end();

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Player plr : getPlayers().values())
            drawPlayerHealthLine(plr);

        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(player.getPosition().x, player.getPosition().y, player.getRadius(), 50);
        shapeRenderer.end();

        batch.begin();
    }

}
