package com.webgame.game.server.handlers;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.server.serialization.dto.player.EnemyDTO;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import com.webgame.game.server.sessions.SessionContainer;
import com.webgame.game.server.utils.ServerUtils;
import io.vertx.core.http.ServerWebSocket;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class CustomWebSocketHandler extends AbstractWebSocketHandler {
    public CustomWebSocketHandler() {
        addLoginDTOListener(event -> {
            LoginDTO loginDTO = event.getLoginDTO();

            PlayerDTO playerDTO = new PlayerDTO();
            playerDTO.setName(loginDTO.getName());
            playerDTO.setId(getSessions().size());
            playerDTO.setPosition(new Vector2(2, 2));

            Long id = playerDTO.getId();

            LoginDTO succesLoginDTO = new LoginDTO(playerDTO);

            getSessions().put(id, event.getWebSocket());
            getPlayers().put(id, playerDTO);

            ServerUtils.writeResponse(event.getWebSocket(), succesLoginDTO, getJsonSerializer());
        });

        addPlayerDTOListener(event -> {
            PlayerDTO playerDTO = event.getPlayerDTO();

          //  if(!getPlayers().containsKey(playerDTO.getId()))
              getPlayers().put(playerDTO.getId(), playerDTO);

            ServerUtils.writeResponseToAll(event.getWebSocket(), new ArrayList<>(getPlayers().values()), getJsonSerializer());
            System.out.println("MSG has been sent");

        });
    }
}
