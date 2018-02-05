package com.webgame.game.events;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.webgame.game.enums.Direction;

public class MoveEvent extends Event {
    private Vector2 vector;
    private Direction direction;


    public MoveEvent(){

    }

    public MoveEvent(Vector2 vector, Direction direction){
        setVector(vector);
        setDirection(direction);
    }

    public Vector2 getVector() {
        return vector;
    }

    public void setVector(Vector2 vector) {
        this.vector = vector;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
