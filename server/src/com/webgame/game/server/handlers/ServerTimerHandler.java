package com.webgame.game.server.handlers;

import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import com.webgame.game.server.utils.ServerUtils;
import io.vertx.core.Handler;
import io.vertx.core.http.ServerWebSocket;

import java.util.concurrent.ConcurrentHashMap;

public class ServerTimerHandler implements Handler<Long> {
    private final JsonSerializer jsonSerializer = new JsonSerializer();

    private final ConcurrentHashMap<Long, ServerWebSocket> sessions;
    private final ConcurrentHashMap<Long, PlayerDTO> players;

    public ServerTimerHandler(final ConcurrentHashMap<Long, ServerWebSocket> sessions, final ConcurrentHashMap<Long, PlayerDTO> players) {
        this.sessions = sessions;
        this.players = players;
    }

    @Override
    public void handle(Long event) {
        ServerUtils.writeResponseToAll(sessions.values(), players.values(), jsonSerializer);
        System.out.println("MSG has been sent");
    }
}
