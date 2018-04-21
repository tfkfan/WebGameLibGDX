package com.webgame.common.client.server.dto.event.listeners;

import com.webgame.common.client.server.dto.event.DTOEvent;

@FunctionalInterface
public interface DTOEventListener<T extends DTOEvent> {
    void handle(T event);
}
