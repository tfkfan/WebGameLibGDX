package com.webgame.game.events.ws;

import com.github.czyzby.websocket.WebSocket;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;

public class PlayerWSEvent extends AbstractWSEvent {
    PlayerDTO playerDTO;

    public PlayerWSEvent(WebSocket webSocket, PlayerDTO playerDTO) {
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
