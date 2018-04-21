package com.webgame.server_app.event.impl;

import com.webgame.common.server.entities.Player;
import com.webgame.server_app.event.AbstractDTOEvent;
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
