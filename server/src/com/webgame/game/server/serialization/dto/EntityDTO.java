package com.webgame.game.server.serialization.dto;

public class EntityDTO implements  DTO {
    protected String id;

    public EntityDTO(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
