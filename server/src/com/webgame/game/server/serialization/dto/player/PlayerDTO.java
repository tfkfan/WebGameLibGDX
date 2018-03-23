package com.webgame.game.server.serialization.dto.player;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.entities.player.Player;
import com.webgame.game.server.serialization.dto.UpdatableDTO;

public class PlayerDTO implements UpdatableDTO<Player> {
    protected long id;
    protected Vector2 position;


    public PlayerDTO(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }


    @Override
    public void updateBy(Player entity) {
        setId(entity.getId());
        setPosition(entity.getPosition());
    }

}
