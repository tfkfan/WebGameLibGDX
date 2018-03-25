package com.webgame.game.server.serialization.dto.player;

import com.webgame.game.server.serialization.dto.DTO;

public class LoginDTO extends PlayerDTO {

    public LoginDTO() {

    }

    public LoginDTO(String name) {
        setName(name);
    }

    public LoginDTO(PlayerDTO playerDTO) {
        setId(playerDTO.getId());
        setName(playerDTO.getName());
        setPosition(playerDTO.getPosition());
    }

}
