package com.webgame.game.events;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.webgame.game.entities.player.Player;

public class PlayerDamagedEvent extends Event {
    Player player;
    Integer damage;

    public PlayerDamagedEvent() {

    }

    public PlayerDamagedEvent(Player player, Integer damage) {
        setPlayer(player);
        setDamage(damage);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }
}
