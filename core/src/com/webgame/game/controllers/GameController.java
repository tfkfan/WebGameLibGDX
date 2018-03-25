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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.webgame.game.Configs;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.skill.AOESkill;
import com.webgame.game.entities.skill.SingleSkill;
import com.webgame.game.entities.skill.Skill;
import com.webgame.game.entities.skill.StaticSkill;
import com.webgame.game.enums.MoveState;
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

import java.util.List;

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
            PlayerWSEvent playerWSEvent = (PlayerWSEvent) event;
            PlayerDTO playerDTO = playerWSEvent.getPlayerDTO();
            Player player = getPlayer();
            player.setPosition(playerDTO.getPosition());
            return true;
        });

        addEnemyWSListener(event -> {
            EnemyWSEvent enemyWSEvent = (EnemyWSEvent) event;
            EnemyDTO enemyDTO = enemyWSEvent.getEnemyDTO();
            if (!getPlayers().containsKey(enemyDTO.getId())) {
                Gdx.app.postRunnable(() -> {
                    synchronized (world) {
                        Player player = Player.createPlayer(world);
                        player.getAttributes().setName(enemyDTO.getName());
                        player.setPosition(enemyDTO.getPosition());
                        player.setId(enemyDTO.getId());

                        getPlayers().put(player.getId(), player);
                    }
                });
            } else {
                Gdx.app.log("WS", "Id: " + enemyDTO.getId() + " currPlayer: " + player.getId());
                getPlayers().get(enemyDTO.getId()).setPosition(enemyDTO.getPosition());
            }
            return true;
        });
    }

    public void playerLogin(String username, String password) {
        getSocketService().connect();
        getSocketService().send(new LoginDTO(username));
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if (player == null)
            return;

        if (player != null) {
            handleInput();
            player.update(dt);
        }
        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);

        for (Player currentPlayer : getPlayers().values()) {
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

        worldRenderer.render();

        //NOT REMOVE!
        batch.end();
        batch.begin();

        super.draw(batch, parentAlpha);

        if (player != null && player.getAttributes().getName() != null) {
            player.draw(batch, parentAlpha);
            font.draw(batch, player.getAttributes().getName(), player.getPosition().x - player.getWidth() / 2, player.getPosition().y + player.getHeight() + 5 / Configs.PPM);
        }

        if (player != null) {
            List<Skill> skills = player.getActiveSkills();
            for (Skill skill : skills)
                skill.draw(batch, parentAlpha);
        }

        if (players != null) {

            for (Player enemy : players.values()) {
                if (enemy.equals(player))
                    continue;
                enemy.draw(batch, parentAlpha);
            }
        }
        //drawing figures(hp)
        batch.end();

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setColor(Color.GREEN);


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (player != null)
            drawPlayerHealthLine(player);

        if (players != null)

            for (Player enemy : players.values()) {
                if (enemy.equals(player))
                    continue;

                drawPlayerHealthLine(enemy);
            }
        shapeRenderer.end();

        if (player != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.circle(player.getPosition().x, player.getPosition().y, player.getRadius(), 50);
            shapeRenderer.end();
        }

        batch.begin();
    }
}
