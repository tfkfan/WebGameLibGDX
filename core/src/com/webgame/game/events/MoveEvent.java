package com.webgame.game.events;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;

public class MoveEvent extends Event {
    private Vector2 vector;
    public MoveEvent(){

    }

    public MoveEvent(Vector2 vector){
        setVector(vector);
    }

    public Vector2 getVector() {
        return vector;
    }

    public void setVector(Vector2 vector) {
        this.vector = vector;
    }
}
