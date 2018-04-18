package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import com.webgame.game.Configs;
import com.webgame.game.entities.player.ClientPlayer;
import com.webgame.game.enums.DirectionState;
import com.webgame.game.enums.PlayerAttackState;
import com.webgame.game.enums.PlayerMoveState;
import com.webgame.game.events.AttackEvent;
import com.webgame.game.events.MoveEvent;
import com.webgame.game.events.PlayerDamagedEvent;
import com.webgame.game.events.listeners.AttackListener;
import com.webgame.game.events.listeners.PlayerDamagedListener;
import com.webgame.game.events.listeners.PlayerMoveListener;
import com.webgame.game.events.listeners.ws.AttackWSListener;
import com.webgame.game.events.listeners.ws.PlayersWSListener;
import com.webgame.game.events.listeners.ws.SuccesLoginWSListener;
import com.webgame.game.events.ws.AttackWSEvent;
import com.webgame.game.events.ws.LoginSuccessEvent;
import com.webgame.game.events.ws.PlayersWSEvent;
import com.webgame.game.server.dto.AttackDTO;
import com.webgame.game.server.dto.LoginDTO;
import com.webgame.game.server.entities.Player;
import com.webgame.game.stages.GameStage;
import com.webgame.game.utils.GameUtils;
import com.webgame.game.world.WorldRenderer;
import com.webgame.game.ws.JsonWebSocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractGameController extends AbstractController implements InputProcessor, WebSocketListener, EventListener {
    protected SpriteBatch batch;
    protected ShapeRenderer shapeRenderer;
    protected BitmapFont font;

    protected World world;
    protected WorldRenderer worldRenderer;

    protected OrthographicCamera camera;
    protected Viewport viewport;

    protected final List<EventListener> loginWSEventList = Collections.synchronizedList(new ArrayList<EventListener>());
    protected final List<EventListener> playersWSEventList = Collections.synchronizedList(new ArrayList<EventListener>());
    protected final List<EventListener> attackWSEventList = Collections.synchronizedList(new ArrayList<EventListener>());

    protected final List<EventListener> playerMoveListeners = Collections.synchronizedList(new ArrayList<EventListener>());
    protected final List<EventListener> attackListeners = Collections.synchronizedList(new ArrayList<EventListener>());
    protected final List<EventListener> playerDamagedListeners = Collections.synchronizedList(new ArrayList<EventListener>());


    public AbstractGameController(OrthographicCamera camera, Viewport viewport) {
        Gdx.input.setInputProcessor(this);

        this.camera = camera;
        this.viewport = viewport;

        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        this.font = new BitmapFont();
        this.font.setUseIntegerPositions(false);
        this.font.getData().setScale(1 / Configs.PPM);

        this.world = new World(new Vector2(0, 0), true);
        this.worldRenderer = new WorldRenderer(world, camera);

        initSocketService();

        addListener(this);

    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof MoveEvent) {
            for (EventListener listener : playerMoveListeners)
                listener.handle(event);
        } else if (event instanceof AttackEvent) {
            //cooldown
            for (EventListener listener : attackListeners)
                listener.handle(event);
        } else if (event instanceof PlayerDamagedEvent) {
            for (EventListener listener : playerDamagedListeners)
                listener.handle(event);
        } else if (event instanceof LoginSuccessEvent) {
            for (EventListener listener : loginWSEventList)
                listener.handle(event);
        } else if (event instanceof PlayersWSEvent) {
            for (EventListener listener : playersWSEventList)
                listener.handle(event);
        } else if (event instanceof AttackWSEvent) {
            for (EventListener listener : attackWSEventList)
                listener.handle(event);
        }
        return true;
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
    public boolean onMessage(WebSocket webSocket, final byte[] packet) {
        try {
            final JsonSerializer jsonSerializer = new JsonSerializer();
            final Object res = jsonSerializer.deserialize(packet);

            if (res instanceof LoginDTO) {
                LoginDTO loginDTO = (LoginDTO) res;
                LoginSuccessEvent event = new LoginSuccessEvent(webSocket, loginDTO);
                fire(event);
            } else if (res instanceof Array) {
                Array<Player> serverPlayers = (Array<Player>) res;
                PlayersWSEvent event = new PlayersWSEvent(webSocket, serverPlayers);
                fire(event);
                //Gdx.app.log("WS", "PLAYERS");
            } else if (res instanceof AttackDTO) {
                AttackDTO attackDTO = (AttackDTO) res;
                AttackWSEvent attackWSEvent = new AttackWSEvent(webSocket, attackDTO);
                fire(attackWSEvent);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        // Gdx.app.log("WS", "!" + res.getClass().getName());
        return true;
    }

    @Override
    public boolean onError(WebSocket webSocket, Throwable error) {
        Gdx.app.log("WS", error.getMessage());
        int id = webSocket.getState().getId();

        return false;
    }

    private void initSocketService() {
        setSocketService(new JsonWebSocket() {

            @Override
            protected WebSocketListener createListener() {
                return AbstractGameController.this;
            }
        });
    }

    public void addAttackWSListener(AttackWSListener listener) {
        attackWSEventList.add(listener);
    }

    public void addAttackListener(AttackListener listener) {
        attackListeners.add(listener);
    }

    public void addPlayerDamagedListener(PlayerDamagedListener listener) {
        playerDamagedListeners.add(listener);
    }

    public void addPlayerMoveListener(PlayerMoveListener listener) {
        playerMoveListeners.add(listener);
    }

    public void addPlayersWSListener(PlayersWSListener listener) {
        playersWSEventList.add(listener);
    }

    public void addSuccessLoginWSListener(SuccesLoginWSListener loginEventListener) {
        loginWSEventList.add(loginEventListener);
    }

    protected void handleInput() {
        float d = 5f;
        DirectionState directionState = clientPlayer.getOldDirectionState();

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

        if (velocity.x != 0 || velocity.y != 0 || clientPlayer.getCurrAnimationState().equals(PlayerMoveState.WALK)
                || clientPlayer.getCurrentAttackState().equals(PlayerAttackState.BATTLE))
            fire(new MoveEvent(velocity, directionState, clientPlayer));
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            Vector3 trg = this.getStage().getCamera().unproject(new Vector3(screenX, screenY, 0), getStage().getViewport().getScreenX(), getStage().getViewport().getScreenY(),
                    getStage().getViewport().getScreenWidth(), getStage().getViewport().getScreenHeight());

            Vector2 target = new Vector2(trg.x, trg.y);
            fire(new AttackEvent(target, clientPlayer));
            return true;
        }
        return false;
    }


    public void drawPlayerHealthLine(ClientPlayer clientPlayer) {
        shapeRenderer.rect(clientPlayer.getPosition().x - clientPlayer.getWidth() / 2, clientPlayer.getPosition().y + clientPlayer.getHeight() - 20 / Configs.PPM,
                GameUtils.calcHealthLineWidth(clientPlayer.getWidth(), clientPlayer.getHealthPoints(), clientPlayer.getMaxHealthPoints()), 5 / Configs.PPM);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode >= 8 && keycode <= 16) {
            int skillIndex = keycode - 8;
            clientPlayer.setCurrentSkillIndex(skillIndex);
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
