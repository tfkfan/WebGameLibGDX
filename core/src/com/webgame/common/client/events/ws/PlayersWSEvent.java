package com.webgame.common.client.events.ws;

import com.badlogic.gdx.utils.Array;
import com.github.czyzby.websocket.WebSocket;
import com.webgame.common.server.entities.Player;


public class PlayersWSEvent extends AbstractWSEvent {
    Array<Player> players;

    public PlayersWSEvent(WebSocket webSocket, Array players) {
        super(webSocket);
        setPlayers(players);
    }

    public Array<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Array<Player> players) {
        this.players = players;
    }
}
