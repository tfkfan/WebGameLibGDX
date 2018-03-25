package com.webgame.game.server.handlers;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.server.serialization.dto.player.EnemyDTO;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import com.webgame.game.server.sessions.SessionContainer;
import io.vertx.core.http.ServerWebSocket;

public class CustomWebSocketHandler extends AbstractWebSocketHandler {
    public CustomWebSocketHandler() {
        addLoginDTOListener(event -> {
            LoginDTO loginDTO = event.getLoginDTO();
            if (!getSessions().containsKey(loginDTO)) {

                PlayerDTO playerDTO = new PlayerDTO();
                playerDTO.setName(loginDTO.getName());
                playerDTO.setId(getSessions().size());
                playerDTO.setPosition(new Vector2(2, 2));

                EnemyDTO enemyDTO = new EnemyDTO(playerDTO);
                LoginDTO succesLoginDTO = new LoginDTO(playerDTO);
                getSessions().put(playerDTO.getId(), new SessionContainer(playerDTO, event.getWebSocket()));

                writeResponse(event.getWebSocket(), succesLoginDTO);
                writeResponseToAllExcept(event.getWebSocket(), enemyDTO);
            }
        });

        addPlayerDTOListener(event -> {
            ServerWebSocket webSocket = event.getWebSocket();
            PlayerDTO playerDTO = event.getPlayerDTO();
            EnemyDTO enemyDTO = new EnemyDTO(playerDTO);
            writeResponse(webSocket, playerDTO);
            writeResponseToAllExcept(webSocket, enemyDTO);
        });
    }
}
