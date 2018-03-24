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
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import com.webgame.game.Configs;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.player.impl.Knight;
import com.webgame.game.events.AttackEvent;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerConnectedDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import com.webgame.game.world.WorldRenderer;
import com.webgame.game.entities.player.impl.Mage;
import com.webgame.game.ws.IWebSocket;
import com.webgame.game.ws.JsonWebSocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GameController extends AbstractController implements InputProcessor, WebSocketListener {
    private SpriteBatch batch;

    private World world;
    private WorldRenderer worldRenderer;

    private OrthographicCamera camera;
    private Viewport viewport;

    private PlayerController pController;
    private SkillController sController;

    public GameController(OrthographicCamera camera, Viewport viewport) {
        Gdx.input.setInputProcessor(this);

        this.camera = camera;
        this.viewport = viewport;

        this.batch = new SpriteBatch();

        this.world = new World(new Vector2(0, 0), true);
        this.worldRenderer = new WorldRenderer(world, camera);

        initSocketService();
        setPlayers(new ConcurrentHashMap<Long, Player>());

        this.pController = new PlayerController();
        this.sController = new SkillController();

        pController.setPlayers(getPlayers());
        sController.setPlayers(getPlayers());
        pController.setSocketService(getSocketService());
        sController.setSocketService(getSocketService());

        // Player enemy1 = new Knight(batch, Configs.PLAYERSHEETS_FOLDER + "/knight.png");
        //  enemy1.createObject(world);
        // this.players.put(enemy1);

        this.addActor(pController);
        this.addActor(sController);
    }

    public void playerLogin(String username, String password) {
        socketService.connect();

        LoginDTO dto = new LoginDTO();
        dto.setName(username);
        socketService.send(dto);
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
        Gdx.app.log("websocket", "String MSG from server: " + packet);
        return false;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, byte[] packet) {
        final JsonSerializer jsonSerializer = new JsonSerializer();
        final Object res = jsonSerializer.deserialize(packet);

        // Gdx.app.log("websocket", " Object MSG from server: " + res.toString());


        if (res instanceof PlayerDTO) {
            Gdx.app.postRunnable(() -> {
                PlayerDTO playerDTO = (PlayerDTO) res;

                Gdx.app.log("websocket", "Player created " + playerDTO.getId());


                Player player = Player.createPlayer(world);

                player.getAttributes().setName(playerDTO.getName());
                player.setPosition(playerDTO.getPosition());
                player.setId(playerDTO.getId());
                setPlayer(player);
                sController.setPlayer(player);
                pController.setPlayer(player);
                getPlayers().put(player.getId(), player);
            });
        } else if (res instanceof PlayerDTO) {
            PlayerDTO playerDTO = (PlayerDTO) res;
            if (!getPlayers().containsKey(playerDTO.getId())) {
                Player player = Player.createPlayer(world);
                player.getAttributes().setName(playerDTO.getName());
                player.setPosition(playerDTO.getPosition());
                player.setId(playerDTO.getId());

                getPlayers().put(player.getId(), player);
            } else
                getPlayers().get(playerDTO.getId()).setPosition(playerDTO.getPosition());
        }


        return false;
    }

    @Override
    public boolean onError(WebSocket webSocket, Throwable error) {
        return false;
    }

    private void initSocketService() {
        setSocketService(new JsonWebSocket() {

            @Override
            protected WebSocketListener createListener() {
                return GameController.this;
            }
        });
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        if (player == null)
            return;

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
