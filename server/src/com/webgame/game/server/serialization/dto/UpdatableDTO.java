package com.webgame.game.server.serialization.dto;

public interface UpdatableDTO<T extends Object> extends DTO{
    void updateBy(T entity);
}
