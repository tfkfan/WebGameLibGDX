package com.webgame.game.server.serialization.dto.entity;

import com.webgame.game.server.serialization.dto.DTO;

public class EntityDTO implements DTO {
    protected String id;
    protected String name;

    public EntityDTO(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
