package com.webgame.common.client.server.dto.event.impl;

import com.webgame.common.client.server.dto.event.AbstractDTOEvent;
import com.webgame.common.client.server.entities.Player;
import io.vertx.core.http.ServerWebSocket;

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
