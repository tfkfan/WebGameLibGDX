package com.webgame.game.server.handlers;

import com.webgame.game.entities.player.Player;
import io.vertx.core.http.ServerWebSocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CustomWebSocketHandler extends AbstractWebSocketHandler {

    private final ConcurrentHashMap<Long, Player> players = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, ServerWebSocket> sessions = new ConcurrentHashMap<>();

    public CustomWebSocketHandler() {
    }

    @Override
    public void afterDeserializationHandler(final ServerWebSocket webSocket, final Object obj) {
        if (obj == null)
            return;

        if (obj instanceof Player) {
            Player player = (Player) obj;
            if (!sessions.containsKey(player)) {
                sessions.put(player.getId(), webSocket);
                players.put(player.getId(), player);
            }
            playerHandler(player);
        }
    }

    public void playerHandler(Player player) {
        //if (!players.contains(player))
        //  players.add(player);

    }

    public void sendToAll(final ServerWebSocket webSocket, final Object obj) {
        for (ServerWebSocket session : sessions.values()) {
            this.writeResponse(session, obj);
        }
    }
}
