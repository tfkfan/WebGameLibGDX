package com.webgame.client.events;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.webgame.client.entities.player.ClientPlayer;

public class AttackEvent extends Event {
    Vector2 targetVector;

    ClientPlayer clientPlayer;

    public AttackEvent() {

    }

    public AttackEvent(Vector2 target,  ClientPlayer clientPlayer) {
        setTarget(target);
        setClientPlayer(clientPlayer);
    }

    public Vector2 getTargetVector() {
        return targetVector;
    }

    public void setTarget(Vector2 target) {
        this.targetVector = target;
    }

    public ClientPlayer getClientPlayer() {
        return clientPlayer;
    }

    public void setClientPlayer(ClientPlayer clientPlayer) {
        this.clientPlayer = clientPlayer;
    }
}
