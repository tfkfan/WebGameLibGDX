package com.webgame.server_app.event.listeners;

import com.webgame.server_app.event.DTOEvent;

@FunctionalInterface
public interface DTOEventListener<T extends DTOEvent> {
    void handle(T event);
}
