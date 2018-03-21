package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketHandler;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import com.webgame.game.Configs;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.player.impl.Knight;
import com.webgame.game.events.AttackEvent;
import com.webgame.game.world.WorldRenderer;
import com.webgame.game.entities.player.impl.Mage;
import com.webgame.game.ws.IWebSocket;
import com.webgame.game.ws.JsonWebSocket;
import com.webgame.game.ws.server.ServerResponse;

import java.util.ArrayList;
import java.util.List;

public class GameController extends AbstractController implements InputProcessor, WebSocketListener {
    private SpriteBatch batch;

    private World world;
    private WorldRenderer worldRenderer;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Player player;
    private List<Player> enemies;

    private PlayerController pController;
    private SkillController sController;

    private IWebSocket socketService;


    public GameController(OrthographicCamera camera, Viewport viewport) {
        Gdx.input.setInputProcessor(this);

        this.enemies = new ArrayList<Player>();

        this.camera = camera;
        this.viewport = viewport;

        this.batch = new SpriteBatch();

        this.world = new World(new Vector2(0, 0), true);
        this.worldRenderer = new WorldRenderer(world, camera);

        this.pController = new PlayerController();
        this.sController = new SkillController();

        Player enemy1 = new Knight(batch, Configs.PLAYERSHEETS_FOLDER + "/knight.png");
        enemy1.createObject(world);
        this.enemies.add(enemy1);

        this.addActor(pController);
        this.addActor(sController);
    }

    public void playerLogin(String username, String password) {
        this.player = new Mage(batch, Configs.PLAYERSHEETS_FOLDER + "/mage.png");
        this.player.getAttributes().setName(username);
        this.player.createObject(world);
        this.player.setPosition(2f, 2f);

        this.pController.init(player, enemies);
        this.sController.init(player, enemies);

        socketService = getSocketService();
        socketService.connect();
        socketService.send("hi");
    }

    @Override
    public boolean onOpen(WebSocket webSocket) {
        return false;
    }

    @Override
    public boolean onClose(WebSocket webSocket, WebSocketCloseCode code, String reason) {
        return false;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String packet) {
        Gdx.app.log("websocket", "MSG from server: " + packet);
        return false;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, byte[] packet) {
        Gdx.app.log("websocket", "MSG from server: " + packet);
        return false;
    }

    @Override
    public boolean onError(WebSocket webSocket, Throwable error) {
        return false;
    }


    private IWebSocket getSocketService() {
        return new JsonWebSocket() {

            @Override
            protected WebSocketListener createListener() {
                return GameController.this;
            }
        };
    }


    @Override
    public void act(float dt) {
        super.act(dt);
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

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode >= 8 && keycode <= 16) {
            int skillIndex = keycode - 8;
            player.setCurrentSkill(skillIndex);
            Gdx.app.log("CurrentSkill", "" + skillIndex);
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
            sController.fire(new AttackEvent(target, player));
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
