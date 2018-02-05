package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.sun.xml.internal.bind.v2.runtime.AttributeAccessor;
import com.webgame.game.Configs;
import com.webgame.game.entities.Enemy;
import com.webgame.game.entities.Player;
import com.webgame.game.enums.Direction;
import com.webgame.game.enums.PlayerState;
import com.webgame.game.events.AttackEvent;
import com.webgame.game.events.MoveEvent;
import com.webgame.game.stages.GameStage;
import com.webgame.game.world.player.impl.Mage;
import java.util.List;

public class PlayerController extends AbstractController implements InputProcessor, EventListener{
    private Player player;

    public PlayerController() {
        Gdx.input.setInputProcessor(this);
    }

    public void init(Player player, List<Enemy> enemies) {
        this.player = player;
        this.addActor(player);
        if (enemies != null)
            for (Actor a : enemies)
                addActor(a);

        this.addListener(this);
    }

    @Override
    public boolean handle (Event event){
        if (event instanceof MoveEvent) {

            Vector2 vec = ((MoveEvent) event).getVector();

            if(!player.getState().equals(PlayerState.ATTACK)
                    || player.getState().equals(PlayerState.ATTACK) && player.isAttackFinished()) {
                if (vec.isZero()) {
                    player.setState(PlayerState.STAND);
                }
                else
                    player.setState(PlayerState.WALK);
            }
            player.setOldDirection(player.getDirection());
            player.setDirection(((MoveEvent) event).getDirection());
            player.setVelocity(vec);
            player.applyVelocity();
        }
        else if(event instanceof AttackEvent){
            if(!player.getState().equals(PlayerState.ATTACK)) {

                player.stateTimer = 0;
                player.setState(PlayerState.ATTACK);
            }
        }
        return true;
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        handleInput();
        if (player.stateTimer >= 10)
            player.stateTimer = 0;
        player.stateTimer += dt;
    }

    private void handleInput() {
        float d = 5f;
        Direction direction = player.getOldDirection();

        if (Gdx.input.isKeyPressed(Input.Keys.Z))
            ((GameStage) this.getStage()).camera.zoom += 0.1;
        if (Gdx.input.isKeyPressed(Input.Keys.X))
            ((GameStage) this.getStage()).camera.zoom -= 0.1;

        Vector2 velocity = new Vector2(0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.x = -d;
            direction = Direction.LEFT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x = d;
            direction = Direction.RIGHT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.y = -d;
            direction = Direction.DOWN;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.y = d;
            direction = Direction.UP;
        }

        if (velocity.x > 0 && velocity.y > 0)
            direction = Direction.UPRIGHT;
        else if (velocity.x > 0 && velocity.y < 0)
            direction = Direction.RIGHTDOWN;
        else if (velocity.x < 0 && velocity.y > 0)
            direction = Direction.UPLEFT;
        else if (velocity.x < 0 && velocity.y < 0)
            direction = Direction.LEFTDOWN;

        fire(new MoveEvent(velocity, direction));
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

            Vector3 target = this.getStage().getCamera().unproject(new Vector3(x, y, 0), getStage().getViewport().getScreenX(),  getStage().getViewport().getScreenY(),
                    getStage().getViewport().getScreenWidth(),  getStage().getViewport().getScreenHeight());


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
