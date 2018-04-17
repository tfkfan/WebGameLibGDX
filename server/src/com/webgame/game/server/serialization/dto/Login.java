package com.webgame.game.server.serialization.dto;

import com.webgame.game.server.entities.Player;

public class Login implements DTO{
    String name;
    String id;
    String password;
    Player player;

    public Login() {

    }

    public Login(String name) {
        setName(name);
    }

    public Login(Player player) {
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
