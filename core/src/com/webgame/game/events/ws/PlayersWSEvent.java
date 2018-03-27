package com.webgame.game.events.ws;

import com.badlogic.gdx.utils.Array;
import com.github.czyzby.websocket.WebSocket;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;


public class PlayersWSEvent extends AbstractWSEvent {
    Array<PlayerDTO> players;

    public PlayersWSEvent(WebSocket webSocket, Array players) {
        super(webSocket);
        setPlayers(players);
    }

    public Array<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(Array<PlayerDTO> players) {
        this.players = players;
    }
}
