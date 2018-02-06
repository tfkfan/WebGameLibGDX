package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.webgame.game.Configs;
import com.webgame.game.entities.Enemy;
import com.webgame.game.entities.Player;
import com.webgame.game.world.WorldRenderer;
import com.webgame.game.world.player.impl.Mage;

import java.util.List;

import static com.webgame.game.Configs.PPM;
import static com.webgame.game.Configs.VIEW_HEIGHT;
import static com.webgame.game.Configs.VIEW_WIDTH;

public class GameController extends AbstractController {
    private SpriteBatch batch;
    private World world;
    private WorldRenderer worldRenderer;
    protected ShapeRenderer sr;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Player player;
    private List<Enemy> enemies;

    private Vector3 target = new Vector3();

    private PlayerController pController;

    public GameController(OrthographicCamera camera, Viewport viewport) {
        this.camera = camera;
        this.viewport = viewport;

        batch = new SpriteBatch();

        world = new World(new Vector2(0, 0), true);
        worldRenderer = new WorldRenderer(world, camera);

        player = new Mage(batch, Configs.PLAYERSHEETS_FOLDER + "/mage.png");
        player.createObject(world);
        enemies = null;

        pController = new PlayerController();
        pController.init(player, enemies);

        this.addActor(pController);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        camera.position.x = player.getPosition().x;
        camera.position.y = player.getPosition().y;
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        // sr.setProjectionMatrix(camera.combined);
        world.step(0.01f, 6, 2);

        //skillPanel.setPosition(camera.position.x - camera.viewportWidth / 2,
        //         camera.position.y - camera.viewportHeight / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldRenderer.render();
        batch.end();
        batch.begin();
        super.draw(batch, parentAlpha);

    }
}
