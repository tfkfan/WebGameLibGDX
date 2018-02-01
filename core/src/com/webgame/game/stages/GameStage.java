package com.webgame.game.stages;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.webgame.game.Configs;
import com.webgame.game.world.WorldRenderer;
import com.webgame.game.world.player.Player;
import com.webgame.game.world.player.impl.Knight;
import com.webgame.game.world.player.impl.Mage;
import com.webgame.game.world.skills.collision.CollisionHandler;

import static com.webgame.game.Configs.VIEW_WIDTH;

import java.util.ArrayList;
import java.util.List;

import static com.webgame.game.Configs.VIEW_HEIGHT;
import static com.webgame.game.Configs.PPM;

public class GameStage extends Stage {
    private SpriteBatch batch;
    private World world;
    private WorldRenderer worldRenderer;
    protected ShapeRenderer sr;

    private final Player player;
    private List<Player> enemies;
    private CollisionHandler clsnHandler;


    public GameStage() {
        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();

        OrthographicCamera camera = new OrthographicCamera();
        camera.position.set(0, 0, 0);
        camera.update();

        world = new World(new Vector2(0, 0), true);
        worldRenderer = new WorldRenderer(world, camera);
        setViewport(new StretchViewport(VIEW_WIDTH / PPM, VIEW_HEIGHT / PPM, camera));

        player = new Mage(batch, "mage.png");
        player.createObject(world);


        Player enemy = new Knight(batch, "knight.png");
        enemy.setPosition(1.5f, 1.5f);
        enemy.createObject(world);
        enemy.getB2body().setTransform(1.5f, 1.5f, 0);

        clsnHandler = new CollisionHandler();

        enemies = new ArrayList<Player>();
        enemies.add(enemy);

        sr = new ShapeRenderer();

        sr.setAutoShapeType(true);

        player.setTouchable(Touchable.enabled);
        player.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                //if (keycode >= 8 && keycode <= 16)
                 //   player.setCurrentSkillIndex(keycode - 8);
                return false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               

                return true;
            }
        });
        setKeyboardFocus(player);
        this.addActor(player);
        this.addActor(enemy);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        handleInput();
        getCamera().update();
        player.update(Configs.TIME_STEP);

        batch.setProjectionMatrix(getCamera().combined);
        sr.setProjectionMatrix(getCamera().combined);
        world.step(0.01f, 6, 2);

        for (Player enemy : enemies) {
            enemy.update(Configs.TIME_STEP);
            clsnHandler.collision(player, enemy);
        }

    }

    @Override
    public void draw() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldRenderer.render();
        // DRAWING GAME OBJECTS
        super.draw();
        sr.begin();
        player.drawShape(sr);
        for (Player enemy : enemies)
            enemy.drawShape(sr);
        sr.end();


    }


    private void handleInput() {
        float d = 5f;
        // if (Gdx.input.isKeyPressed(Input.Keys.A))
        // cam.zoom += 0.1;
        // if (Gdx.input.isKeyPressed(Input.Keys.Q))
        // cam.zoom -= 0.1;

        Vector2 vec = new Vector2(0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            vec.x = -d;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            vec.x = d;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            vec.y = -d;
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            vec.y = d;


        player.setVelocity(vec);
        getCamera().position.x = player.getX();
        getCamera().position.y = player.getY();
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        super.touchDown( x,  y,  pointer, button);
        if (button == Input.Buttons.LEFT) {

            Vector3 target = getCamera().unproject(new Vector3(x, y, 0), getViewport().getScreenX(), getViewport().getScreenY(),
                    getViewport().getScreenWidth(), getViewport().getScreenHeight());

            //player.attack(target.x, target.y);

            return true;
        }
        return true;
    }


}
