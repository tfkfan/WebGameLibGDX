package com.webgame.game.server.serialization.dto.player;

public class EnemyDTO extends PlayerDTO{
    public EnemyDTO(){

    }

    public EnemyDTO(PlayerDTO playerDTO){
        setId(playerDTO.getId());
        setName(playerDTO.getName());
        setPosition(playerDTO.getPosition());
    }
}
