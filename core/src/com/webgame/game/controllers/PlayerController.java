package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.webgame.game.entities.Enemy;
import com.webgame.game.entities.Player;
import com.webgame.game.enums.DirectionState;
import com.webgame.game.enums.PlayerAnimationState;
import com.webgame.game.events.AttackEvent;
import com.webgame.game.events.MoveEvent;
import com.webgame.game.stages.GameStage;

import java.util.List;

public class PlayerController extends AbstractController implements InputProcessor, EventListener {
    private Player player;
    private List<Enemy> enemies;

    public PlayerController() {
        Gdx.input.setInputProcessor(this);
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
        } else if (event instanceof AttackEvent) {
            if (!player.getCurrAnimationState().equals(PlayerAnimationState.ATTACK)) {
                player.clearTimers();
                player.setCurrAnimationState(PlayerAnimationState.ATTACK);
            }
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
        player.draw(batch, parentAlpha);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        if (button == Input.Buttons.LEFT) {

            Vector3 target = this.getStage().getCamera().unproject(new Vector3(x, y, 0), getStage().getViewport().getScreenX(), getStage().getViewport().getScreenY(),
                    getStage().getViewport().getScreenWidth(), getStage().getViewport().getScreenHeight());


            fire(new AttackEvent((target)));
            return true;
        }
        return true;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
