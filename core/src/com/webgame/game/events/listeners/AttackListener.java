package com.webgame.game.events.listeners;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.skill.Skill;
import com.webgame.game.enums.PlayerAnimationState;
import com.webgame.game.events.AttackEvent;

public class AttackListener implements EventListener {

    @Override
    public boolean handle(Event event) {
        Player player = ((AttackEvent)event).getPlayer();
        Skill currentSkill = player.getCurrentSkill();
        if (currentSkill == null)
            return false;

        Long end = currentSkill.getStart() + currentSkill.getCooldown();
        Long currentTime = System.currentTimeMillis();

        if (currentTime < end)
            return false;

        //Casting skill
        if (!player.getCurrAnimationState().equals(PlayerAnimationState.ATTACK)) {
            player.clearTimers();
            player.setCurrAnimationState(PlayerAnimationState.ATTACK);
        }

        player.castSkill(((AttackEvent) event).getTargetVector());
        return true;
    }
}
