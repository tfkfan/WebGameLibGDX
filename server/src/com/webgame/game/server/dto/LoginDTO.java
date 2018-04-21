package com.webgame.common.client.server.dto;

import com.webgame.common.client.server.entities.Player;

public class LoginDTO implements DTO{
    String name;
    String id;
    String password;
    Player player;

    public LoginDTO() {

    }

    public LoginDTO(String name, String password) {
        setName(name);
        setPassword(password);
    }

    public LoginDTO(Player player) {
        setPlayer(player);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
