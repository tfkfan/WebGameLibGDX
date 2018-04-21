package com.webgame.client.events;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.webgame.client.entities.player.ClientPlayer;

public class PlayerDamagedEvent extends Event {
    ClientPlayer clientPlayer;
    Integer damage;

    public PlayerDamagedEvent() {

    }

    public PlayerDamagedEvent(ClientPlayer clientPlayer, Integer damage) {
        setClientPlayer(clientPlayer);
        setDamage(damage);
    }

    public ClientPlayer getClientPlayer() {
        return clientPlayer;
    }

    public void setClientPlayer(ClientPlayer clientPlayer) {
        this.clientPlayer = clientPlayer;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }
}
