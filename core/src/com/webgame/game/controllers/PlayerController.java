package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.webgame.game.entities.player.Enemy;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.DirectionState;
import com.webgame.game.enums.PlayerAnimationState;
import com.webgame.game.events.AttackEvent;
import com.webgame.game.events.MoveEvent;
import com.webgame.game.stages.GameStage;

import java.util.List;

public class PlayerController extends AbstractController implements  EventListener {
    private Player player;
    private List<Enemy> enemies;

    public PlayerController() {

    }

    public void init(Player player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = enemies;
        this.addListener(this);
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof MoveEvent) {
            Vector2 vec = ((MoveEvent) event).getVector();
            player.setOldDirectionState(player.getDirectionState());
            player.setDirectionState(((MoveEvent) event).getDirectionState());
            player.setVelocity(vec);
            player.applyVelocity();
        }
        return true;
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        handleInput();
        player.update(dt);
    }

    private void handleInput() {
        float d = 5f;
        DirectionState directionState = player.getOldDirectionState();

        if (Gdx.input.isKeyPressed(Input.Keys.Z))
            ((GameStage) this.getStage()).camera.zoom += 0.1;
        if (Gdx.input.isKeyPressed(Input.Keys.X))
            ((GameStage) this.getStage()).camera.zoom -= 0.1;

        Vector2 velocity = new Vector2(0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.x = -d;
            directionState = DirectionState.LEFT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x = d;
            directionState = DirectionState.RIGHT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.y = -d;
            directionState = DirectionState.DOWN;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.y = d;
            directionState = DirectionState.UP;
        }

        if (velocity.x > 0 && velocity.y > 0)
            directionState = DirectionState.UPRIGHT;
        else if (velocity.x > 0 && velocity.y < 0)
            directionState = DirectionState.RIGHTDOWN;
        else if (velocity.x < 0 && velocity.y > 0)
            directionState = DirectionState.UPLEFT;
        else if (velocity.x < 0 && velocity.y < 0)
            directionState = DirectionState.LEFTDOWN;

        fire(new MoveEvent(velocity, directionState));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        player.draw(batch, parentAlpha);
    }
}
