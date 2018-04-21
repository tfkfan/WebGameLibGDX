package com.webgame.server.dto.event.listeners;

import com.webgame.server.dto.event.DTOEvent;

@FunctionalInterface
public interface DTOEventListener<T extends DTOEvent> {
    void handle(T event);
}
