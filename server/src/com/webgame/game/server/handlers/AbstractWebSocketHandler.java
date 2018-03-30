package com.webgame.game.server.handlers;

import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import com.webgame.game.entities.player.Player;
import com.webgame.game.server.ServerApp;
import com.webgame.game.server.serialization.dto.event.impl.LoginDTOEvent;
import com.webgame.game.server.serialization.dto.event.impl.PlayerDTOEvent;
import com.webgame.game.server.serialization.dto.event.listeners.DTOEventListener;
import com.webgame.game.server.serialization.dto.event.listeners.LoginDTOEventListener;
import com.webgame.game.server.serialization.dto.event.listeners.PlayerDTOEventListener;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import com.webgame.game.server.sessions.SessionContainer;
import com.webgame.game.server.utils.ServerUtils;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocketFrame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractWebSocketHandler implements Handler<ServerWebSocket> {
    public static final int delay = 20;
    private final JsonSerializer jsonSerializer = new JsonSerializer();

    private final ConcurrentHashMap<Long, ServerWebSocket> sessions;
    private final ConcurrentHashMap<Long, PlayerDTO> players;

    private final List<DTOEventListener> loginEventList = Collections.synchronizedList(new ArrayList<DTOEventListener>());
    private final List<DTOEventListener> playerEventList = Collections.synchronizedList(new ArrayList<DTOEventListener>());

    private Long threadNum = -1L;

    public AbstractWebSocketHandler() {
        sessions = new ConcurrentHashMap<>();
        players = new ConcurrentHashMap<>();

        threadNum = ServerApp.vertx.setPeriodic(delay, event -> {
            // System.out.println("#periodic#");
            if(sessions.isEmpty())
                return;

            ServerUtils.writeResponseToAll(sessions.values(), new ArrayList<>(getPlayers().values()), getJsonSerializer());
            System.out.println("MSG has been sent");

        });
    }

    @Override
    public void handle(final ServerWebSocket webSocket) {
        webSocket.binaryMessageHandler(event -> {
            handleJsonFrame(webSocket, event.getBytes());
        });
    }

    private void handleJsonFrame(final ServerWebSocket webSocket, final byte[] packet) {
        try {
            final Object deserialized = jsonSerializer.deserialize(packet);

            afterDeserializationHandler(webSocket, deserialized);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void afterDeserializationHandler(final ServerWebSocket webSocket, final Object obj) {
        if (obj == null)
            return;

        if (obj instanceof LoginDTO) {
            LoginDTO loginDTO = (LoginDTO) obj;
            LoginDTOEvent event = new LoginDTOEvent(webSocket, loginDTO);
            for (DTOEventListener listener : loginEventList)
                listener.handle(event);
        } else if (obj instanceof PlayerDTO) {
            PlayerDTO playerDTO = (PlayerDTO) obj;
            PlayerDTOEvent event = new PlayerDTOEvent(webSocket, playerDTO);
            for (DTOEventListener listener : playerEventList)
                listener.handle(event);
        }
    }

    public void addPlayerDTOListener(PlayerDTOEventListener listener) {
        playerEventList.add(listener);
    }

    public void addLoginDTOListener(LoginDTOEventListener listener) {
        loginEventList.add(listener);
    }

    protected ConcurrentHashMap<Long, ServerWebSocket> getSessions() {
        return sessions;
    }

    protected ConcurrentHashMap<Long, PlayerDTO> getPlayers(){
        return players;
    }

    protected JsonSerializer getJsonSerializer(){
        return jsonSerializer;
    }
}
