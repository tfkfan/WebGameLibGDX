package com.webgame.game.server.serialization.dto.event.listeners;

import com.webgame.game.server.serialization.dto.event.DTOEvent;

public interface DTOEventListener<T extends DTOEvent> {
    void handle(T event);
}
