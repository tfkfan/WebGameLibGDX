package com.webgame.common.client.events;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.webgame.common.server.entities.Entity;

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
