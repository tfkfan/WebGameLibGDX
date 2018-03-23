package com.webgame.game.server.serialization.dto.player;

import com.badlogic.gdx.math.Vector2;

public class PlayerMoveEventDTO {

    private Long playerId;
    private Vector2 position;
    private Vector2 velocity;

    PlayerMoveEventDTO() {

    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}
