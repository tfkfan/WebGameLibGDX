package com.webgame.client.events;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.webgame.client.entities.player.ClientPlayer;
import com.webgame.client.enums.DirectionState;

public class MoveEvent extends Event {
    private Vector2 vector;
    private DirectionState directionState;
    private ClientPlayer clientPlayer;

    public MoveEvent(){

    }

    public MoveEvent(Vector2 vector, DirectionState directionState, ClientPlayer clientPlayer){
        setVector(vector);
        setDirectionState(directionState);
        setClientPlayer(clientPlayer);
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

    public ClientPlayer getClientPlayer() {
        return clientPlayer;
    }

    public void setClientPlayer(ClientPlayer clientPlayer) {
        this.clientPlayer = clientPlayer;
    }
}
