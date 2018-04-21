package com.webgame.server.dto.event.impl;

import com.webgame.server.dto.event.AbstractDTOEvent;
import io.vertx.core.http.ServerWebSocket;
import com.webgame.server.entities.Player;

public class PlayerDTOEvent extends AbstractDTOEvent {
    Player playerDTO;

    public PlayerDTOEvent(ServerWebSocket webSocket, Player playerDTO) {
        super(webSocket);
        setPlayerDTO(playerDTO);
    }

    public Player getPlayerDTO() {
        return playerDTO;
    }

    public void setPlayerDTO(Player playerDTO) {
        this.playerDTO = playerDTO;
    }
}
