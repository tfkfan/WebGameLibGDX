package com.webgame.common.client.server.dto;

public interface UpdatableDTO<T extends Object> extends DTO{
    void updateBy(T entity);
}
