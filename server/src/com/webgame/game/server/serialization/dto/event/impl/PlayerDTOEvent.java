package com.webgame.game.server.serialization.dto.event.impl;

import com.webgame.game.server.serialization.dto.event.AbstractDTOEvent;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import io.vertx.core.http.ServerWebSocket;

public class PlayerDTOEvent extends AbstractDTOEvent {
    PlayerDTO playerDTO;

    public PlayerDTOEvent(ServerWebSocket webSocket, PlayerDTO playerDTO) {
        super(webSocket);
        setPlayerDTO(playerDTO);
    }

    public PlayerDTO getPlayerDTO() {
        return playerDTO;
    }

    public void setPlayerDTO(PlayerDTO playerDTO) {
        this.playerDTO = playerDTO;
    }
}
