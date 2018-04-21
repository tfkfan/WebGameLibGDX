package com.webgame.common.client.events.ws;

import com.github.czyzby.websocket.WebSocket;
import com.webgame.common.server.entities.Player;

public class PlayerWSEvent extends AbstractWSEvent {
    Player playerDTO;

    public PlayerWSEvent(WebSocket webSocket, Player playerDTO) {
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
