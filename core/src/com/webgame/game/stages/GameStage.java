package com.webgame.game.stages;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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

public class GameStage extends Stage implements InputProcessor {
    public static final float TIME_STEP = 1 / 20f;
    private SpriteBatch batch;
    private World world;
    private WorldRenderer worldRenderer;
    protected ShapeRenderer sr;

    private Player player;
    private List<Player> enemies;
    private CollisionHandler clsnHandler;


    public GameStage(){
        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();

        OrthographicCamera camera = new OrthographicCamera();
        camera.position.set(0, 0, 0);
        camera.update();

        world = new World(new Vector2(0, 0), true);
        worldRenderer = new WorldRenderer(world , camera);
        setViewport( new StretchViewport(VIEW_WIDTH / PPM, VIEW_HEIGHT / PPM, camera));

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

        this.addActor(player);
        this.addActor(enemy);
    }

    @Override
    public void act(float delta) {
        super.act(TIME_STEP);
        handleInput();
        getCamera().update();
        player.update(TIME_STEP);

        batch.setProjectionMatrix(getCamera().combined);
        sr.setProjectionMatrix(getCamera().combined);
        world.step(0.01f, 6, 2);

        for (Player enemy : enemies) {
            enemy.update(TIME_STEP);
            clsnHandler.collision(player, enemy);
        }

    }

    @Override
    public void draw(){

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldRenderer.render();
        // DRAWING GAME OBJECTS
        super.draw();
        batch.begin();

        for (Player enemy : enemies)
            enemy.animateSkills(TIME_STEP);

        player.animateSkills(TIME_STEP);
        batch.end();

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
    public boolean keyDown(int keycode) {
        if(keycode >= 8 && keycode <=16)
            player.setCurrentSkillIndex(keycode - 8);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        if (button == Input.Buttons.LEFT) {

            Vector3 target = getCamera().unproject(new Vector3(x, y, 0), getViewport().getScreenX(), getViewport().getScreenY(),
                   getViewport().getScreenWidth(), getViewport().getScreenHeight());

            player.attack(target.x, target.y);

            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

}
