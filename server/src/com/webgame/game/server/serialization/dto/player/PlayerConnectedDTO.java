package com.webgame.game.server.serialization.dto.player;

public class PlayerConnectedDTO extends PlayerDTO {
    private boolean isConnected;

    public PlayerConnectedDTO() {
        setConnected(false);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
