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
import com.webgame.game.events.listeners.ws.LoginSuccessListener;
import com.webgame.game.events.ws.LoginSuccessEvent;
import com.webgame.game.server.serialization.dto.event.impl.LoginDTOEvent;
import com.webgame.game.server.serialization.dto.event.listeners.impl.LoginDTOEventListener;
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

public class GameController extends AbstractGameController {


    public GameController(OrthographicCamera camera, Viewport viewport) {
       super(camera, viewport);


        addSuccessLoginListener(new LoginSuccessListener() {
            @Override
            public void customHandle(LoginSuccessEvent event) {
                Gdx.app.postRunnable(() -> {
                    LoginDTO playerDTO = event.getLoginDTO();

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
            }
        });
    }
}
