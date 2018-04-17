package com.webgame.game.events;

import com.badlogic.gdx.scenes.scene2d.Event;

public class AnimationFinishedEvent extends Event {
    protected Entity entity;

    public AnimationFinishedEvent() {

    }

    public AnimationFinishedEvent(Entity entity) {
        setEntity(entity);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
