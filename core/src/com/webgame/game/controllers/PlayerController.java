package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.webgame.game.Configs;
import com.webgame.game.entities.player.Enemy;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.DirectionState;
import com.webgame.game.events.MoveEvent;
import com.webgame.game.stages.GameStage;
import com.webgame.game.utils.GameUtils;

import java.util.List;

public class PlayerController extends AbstractController implements EventListener {
    private Player player;
    private List<Player> enemies;

    protected ShapeRenderer shapeRenderer;
    protected BitmapFont font;

    public PlayerController() {
        shapeRenderer = new ShapeRenderer();

        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.getData().setScale(1/Configs.PPM);
    }

    public void init(Player player, List<Player> enemies) {
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
        if (player != null) {
            handleInput();
            player.update(dt);
        }
        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
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

        if (player != null) {
            player.draw(batch, parentAlpha);
            font.draw(batch, player.getAttributes().getName(), player.getPosition().x - player.getWidth()/2, player.getPosition().y + player.getHeight() + 5/Configs.PPM );
        }

        if (enemies != null)
            for (Player enemy : enemies)
                enemy.draw(batch, parentAlpha);
        //drawing figures(hp)
        batch.end();

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setColor(Color.GREEN);


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (player != null)
            drawPlayerHealthLine(player);

        if (enemies != null)
            for (Player enemy : enemies)
                drawPlayerHealthLine(enemy);
        shapeRenderer.end();

        if (player != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.circle(player.getPosition().x, player.getPosition().y, player.getRadius(), 50);
            shapeRenderer.end();
        }

        batch.begin();
    }

    public void drawPlayerHealthLine(Player player) {
        shapeRenderer.rect(player.getPosition().x - player.getWidth() / 2, player.getPosition().y + player.getHeight() - 20 / Configs.PPM,
                GameUtils.calcHealthLineWidth(player.getWidth(), player.getAttributes().getHealthPoints(), player.getAttributes().getMaxHealthPoints()), 5 / Configs.PPM);
    }

}
