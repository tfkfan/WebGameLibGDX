package com.webgame.game.server.handlers;

import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import com.webgame.game.server.serialization.dto.event.impl.LoginDTOEvent;
import com.webgame.game.server.serialization.dto.event.impl.PlayerDTOEvent;
import com.webgame.game.server.serialization.dto.event.listeners.DTOEventListener;
import com.webgame.game.server.serialization.dto.event.listeners.LoginDTOEventListener;
import com.webgame.game.server.serialization.dto.event.listeners.PlayerDTOEventListener;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import io.vertx.core.Handler;
import io.vertx.core.http.ServerWebSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractWebSocketHandler implements Handler<ServerWebSocket> {
    private final JsonSerializer jsonSerializer = new JsonSerializer();

    private final ConcurrentHashMap<Long, ServerWebSocket> sessions;
    private final ConcurrentHashMap<Long, PlayerDTO> players;

    private final List<DTOEventListener> loginEventList = Collections.synchronizedList(new ArrayList<DTOEventListener>());
    private final List<DTOEventListener> playerEventList = Collections.synchronizedList(new ArrayList<DTOEventListener>());

    public AbstractWebSocketHandler() {
        sessions = new ConcurrentHashMap<>();
        players = new ConcurrentHashMap<>();
    }

    @Override
    public void handle(final ServerWebSocket webSocket) {
        webSocket.binaryMessageHandler(event -> {
            Object obj = jsonSerializer.deserialize(event.getBytes());
            if (obj == null)
                return;

            if (obj instanceof LoginDTO) {
                LoginDTO loginDTO = (LoginDTO) obj;
                LoginDTOEvent loginEvent = new LoginDTOEvent(webSocket, loginDTO);
                for (DTOEventListener listener : loginEventList)
                    listener.handle(loginEvent);
            } else if (obj instanceof PlayerDTO) {
                PlayerDTO playerDTO = (PlayerDTO) obj;
                PlayerDTOEvent playerDTOEvent = new PlayerDTOEvent(webSocket, playerDTO);
                for (DTOEventListener listener : playerEventList)
                    listener.handle(playerDTOEvent);
            }
        });
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

    protected ConcurrentHashMap<Long, PlayerDTO> getPlayers() {
        return players;
    }

    protected JsonSerializer getJsonSerializer() {
        return jsonSerializer;
    }
}
