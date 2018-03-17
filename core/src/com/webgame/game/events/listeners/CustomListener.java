package com.webgame.game.events.listeners;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public abstract class CustomListener<T extends Event> implements EventListener{
    @Override
    public boolean handle(Event event) {
        customHandle((T)event);
        return true;
    }

    public abstract void customHandle(T event);
}
