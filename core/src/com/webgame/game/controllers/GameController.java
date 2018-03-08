package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.webgame.game.Configs;
import com.webgame.game.entities.player.Enemy;
import com.webgame.game.entities.player.Player;
import com.webgame.game.events.AttackEvent;
import com.webgame.game.ui.PlayerPanel;
import com.webgame.game.world.WorldRenderer;
import com.webgame.game.entities.player.impl.Mage;

import java.util.List;

public class GameController extends AbstractController implements InputProcessor {
    private SpriteBatch batch;

    private World world;
    private WorldRenderer worldRenderer;
    protected ShapeRenderer shapeRenderer;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Player player;
    private List<Enemy> enemies;

    private PlayerController pController;
    private SkillController sController;


    public GameController(OrthographicCamera camera, Viewport viewport) {
        Gdx.input.setInputProcessor(this);

        this.camera = camera;
        this.viewport = viewport;

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        world = new World(new Vector2(0, 0), true);
        worldRenderer = new WorldRenderer(world, camera);

        player = new Mage(batch, Configs.PLAYERSHEETS_FOLDER + "/mage.png");
        player.createObject(world);

        enemies = null;

        pController = new PlayerController();
        pController.init(player, enemies);

        sController = new SkillController();
        sController.init(player, enemies);

        this.addActor(pController);
        this.addActor(sController);
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

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode >=8 && keycode <=16){
            int skillIndex = keycode - 8;
            player.setCurrentSkill(skillIndex);
        }

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

            Vector3 trg = this.getStage().getCamera().unproject(new Vector3(screenX, screenY, 0), getStage().getViewport().getScreenX(), getStage().getViewport().getScreenY(),
                    getStage().getViewport().getScreenWidth(), getStage().getViewport().getScreenHeight());

            Vector2 target = new Vector2(trg.x, trg.y);
            sController.fire(new AttackEvent((target)));
            return true;
        }
        return false;
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

    public Player getPlayer() {
        return player;
    }
}
