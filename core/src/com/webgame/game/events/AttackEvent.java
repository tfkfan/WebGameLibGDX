package com.webgame.game.events;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;

public class AttackEvent extends Event {
    Vector2 targetVector;

    public AttackEvent() {

    }

    public AttackEvent(Vector2 target) {
        setTarget(target);
    }

    public Vector2 getTargetVector() {
        return targetVector;
    }

    public void setTarget(Vector2 target) {
        this.targetVector = target;
    }
}
