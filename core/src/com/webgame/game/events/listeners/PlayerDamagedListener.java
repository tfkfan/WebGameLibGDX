package com.webgame.game.events.listeners;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.webgame.game.entities.player.Player;
import com.webgame.game.events.PlayerDamagedEvent;

public class PlayerDamagedListener implements EventListener {

    @Override
    public boolean handle(Event event) {
        getPlayerDamaged(((PlayerDamagedEvent) event).getPlayer(), ((PlayerDamagedEvent) event).getDamage());
        return true;
    }

    protected void getPlayerDamaged(Player target, Integer damage) {
        if (target.getAttributes().getHealthPoints() > 0)
            target.getAttributes().setHealthPoints(target.getAttributes().getHealthPoints() - damage);
        else
            target.getAttributes().setHealthPoints(0);
    }
}
