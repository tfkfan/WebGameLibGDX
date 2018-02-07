package com.webgame.game.events;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.webgame.game.enums.DirectionState;

public class MoveEvent extends Event {
    private Vector2 vector;
    private DirectionState directionState;


    public MoveEvent(){

    }

    public MoveEvent(Vector2 vector, DirectionState directionState){
        setVector(vector);
        setDirectionState(directionState);
    }

    public Vector2 getVector() {
        return vector;
    }

    public void setVector(Vector2 vector) {
        this.vector = vector;
    }

    public DirectionState getDirectionState() {
        return directionState;
    }

    public void setDirectionState(DirectionState directionState) {
        this.directionState = directionState;
    }
}
