package com.webgame.game.server.sessions;

import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import io.vertx.core.http.ServerWebSocket;

public class SessionContainer {
    private PlayerDTO playerDTO;
    private ServerWebSocket session;

    public SessionContainer(){

    }

    public SessionContainer(PlayerDTO playerDTO, ServerWebSocket session){
        setPlayerDTO(playerDTO);
        setSession(session);
    }

    public PlayerDTO getPlayerDTO() {
        return playerDTO;
    }

    public void setPlayerDTO(PlayerDTO playerDTO) {
        this.playerDTO = playerDTO;
    }

    public ServerWebSocket getSession() {
        return session;
    }

    public void setSession(ServerWebSocket session) {
        this.session = session;
    }
}
