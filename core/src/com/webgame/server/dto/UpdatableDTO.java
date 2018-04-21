package com.webgame.server.dto;

public interface UpdatableDTO<T extends Object> extends DTO {
    void updateBy(T entity);
}
