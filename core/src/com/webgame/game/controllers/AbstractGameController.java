package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import com.webgame.game.Configs;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.skill.AOESkill;
import com.webgame.game.entities.skill.SingleSkill;
import com.webgame.game.entities.skill.Skill;
import com.webgame.game.entities.skill.StaticSkill;
import com.webgame.game.enums.DirectionState;
import com.webgame.game.enums.MoveState;
import com.webgame.game.enums.PlayerAnimationState;
import com.webgame.game.events.AttackEvent;
import com.webgame.game.events.MoveEvent;
import com.webgame.game.events.PlayerDamagedEvent;
import com.webgame.game.events.listeners.AttackListener;
import com.webgame.game.events.listeners.PlayerDamagedListener;
import com.webgame.game.events.listeners.PlayerMoveListener;
import com.webgame.game.events.listeners.ws.EnemyWSListener;
import com.webgame.game.events.listeners.ws.SuccesLoginWSListener;
import com.webgame.game.events.listeners.ws.PlayerWSListener;
import com.webgame.game.events.ws.EnemyWSEvent;
import com.webgame.game.events.ws.LoginSuccessEvent;
import com.webgame.game.events.ws.PlayerWSEvent;
import com.webgame.game.server.serialization.dto.player.EnemyDTO;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
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
    protected final List<EventListener> playerWSEventList = Collections.synchronizedList(new ArrayList<EventListener>());
    protected final List<EventListener> enemyWSEventList = Collections.synchronizedList(new ArrayList<EventListener>());

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
        } else if (event instanceof PlayerWSEvent) {
            for (EventListener listener : playerWSEventList)
                listener.handle(event);
        } else if (event instanceof EnemyWSEvent) {
            for (EventListener listener : enemyWSEventList)
                listener.handle(event);
        }
        return true;
    }


    @Override
    public boolean onMessage(WebSocket webSocket, byte[] packet) {
        final JsonSerializer jsonSerializer = new JsonSerializer();
        final Object res = jsonSerializer.deserialize(packet);

        if (res instanceof LoginDTO) {
            LoginDTO loginDTO = (LoginDTO) res;
            LoginSuccessEvent event = new LoginSuccessEvent(webSocket, loginDTO);
            fire(event);
        } else if (res instanceof EnemyDTO) {
            EnemyDTO enemyDTO = (EnemyDTO) res;
            EnemyWSEvent event = new EnemyWSEvent(webSocket, enemyDTO);
            fire(event);
        } else if (res instanceof PlayerDTO) {
            PlayerDTO playerDTO = (PlayerDTO) res;
            PlayerWSEvent event = new PlayerWSEvent(webSocket, playerDTO);
            fire(event);
        }

        return true;
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

    public void addPlayerWSListener(PlayerWSListener listener) {
        playerWSEventList.add(listener);
    }

    public void addSuccessLoginWSListener(SuccesLoginWSListener loginEventListener) {
        loginWSEventList.add(loginEventListener);
    }

    public void addEnemyWSListener(EnemyWSListener listener) {
        enemyWSEventList.add(listener);
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if (player == null)
            return;

        if (player != null) {
            handleInput();
            player.update(dt);
        }
        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);

        for (Player currentPlayer : getPlayers().values()) {
            List<Skill> skills = currentPlayer.getActiveSkills();
            for (Skill skill : skills) {
                skill.update(dt);

                if (!(skill instanceof AOESkill) && skill.isMarked())
                    continue;

                for (Player anotherPlayer : getPlayers().values()) {
                    if (anotherPlayer == currentPlayer)
                        continue;

                    //handling collision
                    if (skill instanceof AOESkill) {
                        if (Intersector.overlaps(anotherPlayer.getShape(), ((AOESkill) skill).getArea())) {
                            this.fire(new PlayerDamagedEvent(anotherPlayer, skill.getDamage()));
                        }
                    } else {
                        if (Intersector.overlaps(skill.getShape(), anotherPlayer.getShape())) {
                            if (skill instanceof SingleSkill || skill instanceof StaticSkill) {
                                if (skill.getMoveState().equals(MoveState.STATIC)) {
                                    this.fire(new PlayerDamagedEvent(anotherPlayer, skill.getDamage()));
                                    skill.setMarked(true);
                                }
                            }
                        }
                    }
                }
            }
        }

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

        if (player != null && player.getAttributes().getName() != null) {
            player.draw(batch, parentAlpha);
            font.draw(batch, player.getAttributes().getName(), player.getPosition().x - player.getWidth() / 2, player.getPosition().y + player.getHeight() + 5 / Configs.PPM);
        }

        if (player != null) {
            List<Skill> skills = player.getActiveSkills();
            for (Skill skill : skills)
                skill.draw(batch, parentAlpha);
        }

        if (players != null) {

            for (Player enemy : players.values()) {
                if (enemy.equals(player))
                    continue;
                enemy.draw(batch, parentAlpha);
            }
        }
        //drawing figures(hp)
        batch.end();

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setColor(Color.GREEN);


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (player != null)
            drawPlayerHealthLine(player);

        if (players != null)

            for (Player enemy : players.values()) {
                if (enemy.equals(player))
                    continue;

                drawPlayerHealthLine(enemy);
            }
        shapeRenderer.end();

        if (player != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.circle(player.getPosition().x, player.getPosition().y, player.getRadius(), 50);
            shapeRenderer.end();
        }

        batch.begin();
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

        if (velocity.x != 0 || velocity.y != 0 || player.getCurrAnimationState().equals(PlayerAnimationState.WALK)
                || player.getCurrAnimationState().equals(PlayerAnimationState.ATTACK))
            fire(new MoveEvent(velocity, directionState, player));
    }

    public void playerLogin(String username, String password) {
        socketService.connect();

        LoginDTO dto = new LoginDTO();
        dto.setName(username);
        socketService.send(dto);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (button == Input.Buttons.LEFT) {

            Vector3 trg = this.getStage().getCamera().unproject(new Vector3(screenX, screenY, 0), getStage().getViewport().getScreenX(), getStage().getViewport().getScreenY(),
                    getStage().getViewport().getScreenWidth(), getStage().getViewport().getScreenHeight());

            Vector2 target = new Vector2(trg.x, trg.y);
            fire(new AttackEvent(target, player));
            return true;
        }
        return false;
    }


    public void drawPlayerHealthLine(Player player) {
        shapeRenderer.rect(player.getPosition().x - player.getWidth() / 2, player.getPosition().y + player.getHeight() - 20 / Configs.PPM,
                GameUtils.calcHealthLineWidth(player.getWidth(), player.getAttributes().getHealthPoints(), player.getAttributes().getMaxHealthPoints()), 5 / Configs.PPM);
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
    public boolean onError(WebSocket webSocket, Throwable error) {
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
}
