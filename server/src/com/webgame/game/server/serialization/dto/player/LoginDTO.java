package com.webgame.game.server.serialization.dto.player;

import com.webgame.game.server.serialization.dto.DTO;

public class LoginDTO implements DTO{
    private String username;
    public LoginDTO(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
