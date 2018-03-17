package com.webgame.game.events;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.DirectionState;

public class MoveEvent extends Event {
    private Vector2 vector;
    private DirectionState directionState;
    private Player player;

    public MoveEvent(){

    }

    public MoveEvent(Vector2 vector, DirectionState directionState, Player player){
        setVector(vector);
        setDirectionState(directionState);
        setPlayer(player);
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
