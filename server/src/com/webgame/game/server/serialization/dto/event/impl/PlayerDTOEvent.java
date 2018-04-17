package com.webgame.game.server.serialization.dto.event.impl;

import com.webgame.game.server.serialization.dto.event.AbstractDTOEvent;
import com.webgame.game.server.entities.Player;
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
