package com.webgame.game.server.serialization.dto;

import com.webgame.game.entities.Entity;

public interface UpdatableDTO<T extends Object> extends DTO{
    void updateBy(T entity);
}
