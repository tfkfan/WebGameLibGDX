package com.webgame.game.server.dto.event.listeners;

import com.webgame.game.server.dto.event.DTOEvent;

@FunctionalInterface
public interface DTOEventListener<T extends DTOEvent> {
    void handle(T event);
}
