package com.webgame.game.server.handlers;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerConnectedDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import com.webgame.game.server.sessions.SessionContainer;
import io.vertx.core.http.ServerWebSocket;

import java.util.concurrent.ConcurrentHashMap;

public class CustomWebSocketHandler extends AbstractWebSocketHandler {

    private final ConcurrentHashMap<Long, SessionContainer> sessions = new ConcurrentHashMap<>();

    public CustomWebSocketHandler() {
    }

    @Override
    public void afterDeserializationHandler(final ServerWebSocket webSocket, final Object obj) {
        if (obj == null)
            return;

        if (obj instanceof LoginDTO) {
            LoginDTO player = (LoginDTO) obj;
            if (!sessions.containsKey(player)) {

                PlayerDTO playerDTO = new PlayerDTO();
                playerDTO.setName(player.getUsername());
                playerDTO.setId(sessions.size());
                playerDTO.setPosition(new Vector2(2, 2));

                PlayerConnectedDTO playerConnectedDTO = new PlayerConnectedDTO();
                playerConnectedDTO.setId(playerDTO.getId());
                playerConnectedDTO.setConnected(true);
                playerConnectedDTO.setName(playerDTO.getName());
                playerConnectedDTO.setPosition(playerDTO.getPosition());

                sessions.put(playerDTO.getId(), new SessionContainer(playerDTO, webSocket));

                writeResponse(webSocket, playerConnectedDTO);
                writeResponseToAllExcept(webSocket, playerDTO);
            }
        } else if (obj instanceof PlayerDTO) {
            PlayerDTO playerDTO = (PlayerDTO) obj;
            writeResponseToAll(playerDTO);
        }

    }

    public void writeResponseToAll(final Object obj) {
        for (SessionContainer sessionContainer : sessions.values()) {
            this.writeResponse(sessionContainer.getSession(), obj);
        }
    }

    public void writeResponseToAllExcept(final ServerWebSocket webSocket, final Object obj) {
        for (SessionContainer sessionContainer : sessions.values()) {
            if (sessionContainer.getSession().equals(webSocket))
                continue;

            this.writeResponse(sessionContainer.getSession(), obj);
        }
    }
}
