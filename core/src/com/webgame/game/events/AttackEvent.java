package com.webgame.game.events;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.webgame.game.entities.player.Player;

public class AttackEvent extends Event {
    Vector2 targetVector;

    Player player;

    public AttackEvent() {

    }

    public AttackEvent(Vector2 target,  Player player) {
        setTarget(target);
        setPlayer(player);
    }

    public Vector2 getTargetVector() {
        return targetVector;
    }

    public void setTarget(Vector2 target) {
        this.targetVector = target;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
