package com.webgame.game.events;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;

public class AttackEvent extends Event {
    Vector3 targetVector;

    public AttackEvent() {

    }

    public AttackEvent(Vector3 target) {
        setTarget(target);
    }

    public Vector3 getTargetVector() {
        return targetVector;
    }

    public void setTarget(Vector3 target) {
        this.targetVector = target;
    }
}
