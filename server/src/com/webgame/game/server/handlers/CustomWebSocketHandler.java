package com.webgame.game.server.handlers;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import java.util.ArrayList;
import static  com.webgame.game.server.utils.ServerUtils.writeResponseToAll;
import static  com.webgame.game.server.utils.ServerUtils.writeResponse;
import static com.webgame.game.server.utils.ServerUtils.vertx;

public class CustomWebSocketHandler extends AbstractWebSocketHandler {
    public static final int delay = 20;

    private Long dispatcherThreadId;

    public CustomWebSocketHandler() {
        dispatcherThreadId = vertx.setPeriodic(delay, event -> {
            if(getSessions().isEmpty())
                return;

            writeResponseToAll(getSessions().values(), new ArrayList<>(getPlayers().values()), getJsonSerializer());
        });

        addLoginDTOListener(event -> {
            LoginDTO loginDTO = event.getLoginDTO();

            PlayerDTO playerDTO = new PlayerDTO();
            playerDTO.setName(loginDTO.getName());
            playerDTO.setId(getSessions().size());
            playerDTO.setPosition(new Vector2(2, 2));

            LoginDTO succesLoginDTO = new LoginDTO(playerDTO);

            writeResponse(event.getWebSocket(), succesLoginDTO, getJsonSerializer());
        });

        addPlayerDTOListener(event -> {
            PlayerDTO playerDTO = event.getPlayerDTO();

            Long id = playerDTO.getId();
            getSessions().put(id, event.getWebSocket());
            getPlayers().put(id, playerDTO);
        });
    }
}
