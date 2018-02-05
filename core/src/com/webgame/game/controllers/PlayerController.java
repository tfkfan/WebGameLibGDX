package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.webgame.game.Configs;
import com.webgame.game.entities.Enemy;
import com.webgame.game.entities.Player;
import com.webgame.game.enums.PlayerState;
import com.webgame.game.events.MoveEvent;
import com.webgame.game.stages.GameStage;
import com.webgame.game.world.player.impl.Mage;

import java.util.List;

public class PlayerController extends AbstractController implements InputProcessor{
    private Player player;

    public PlayerController(){
        Gdx.app.getInput().setInputProcessor(this);
    }

    public void init(Player player, List<Enemy> enemies){
        this.player = player;
        this.addActor(player);
        if(enemies != null)
            for(Actor a : enemies)
                addActor(a);

        this.addListener(event -> {
            if(event instanceof MoveEvent){
                Vector2 vec = ((MoveEvent) event).getVector();
                if(vec.isZero())
                    player.setState(PlayerState.STAND);
                else
                    player.setState(PlayerState.WALK);
                player.setVelocity(vec);
                player.applyVelocity();
            }
            return true;
        });
    }

    @Override
    public void act(float dt){
        super.act(dt);
        handleInput();
        if(player.stateTimer >= 10)
            player.stateTimer = 0;
        player.stateTimer+=dt;
    }

    private void handleInput() {
        float d = 5f;
        if (Gdx.input.isKeyPressed(Input.Keys.Z))
            ((GameStage)this.getStage()).camera.zoom += 0.1;
        if (Gdx.input.isKeyPressed(Input.Keys.X))
            ((GameStage)this.getStage()).camera.zoom -= 0.1;

        Vector2 vec = new Vector2(0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            vec.x = -d;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            vec.x = d;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            vec.y = -d;
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            vec.y = d;

        fire(new MoveEvent(vec));
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (button == Input.Buttons.LEFT) {

            //target = getCamera().unproject(new Vector3(x, y, 0), getViewport().getScreenX(), getViewport().getScreenY(),
            //        getViewport().getScreenWidth(), getViewport().getScreenHeight());

            // player.attack(target.x, target.y);

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
