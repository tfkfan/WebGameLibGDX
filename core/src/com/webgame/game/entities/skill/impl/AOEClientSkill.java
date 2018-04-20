package com.webgame.game.entities.skill.impl;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.player.ClientPlayer;
import com.webgame.game.entities.skill.ClientSkill;
import com.webgame.game.entities.skill.SkillSprite;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.MoveState;
import com.webgame.game.server.entities.Skill;

public class AOEClientSkill extends ClientSkill {
    public AOEClientSkill() {
        super();
    }

    public AOEClientSkill(ClientSkill clientSkill) {
        super(clientSkill);
    }

    @Override
    public AOEClientSkill createCopy() {
        return new AOEClientSkill(this);
    }

    @Override
    public void cast(Vector2 targetPosition) {
        for (SkillSprite animation : getAnimations()) {
            animation.setEntityState(EntityState.ACTIVE);
            animation.setMoveState(MoveState.STATIC);
        }
    }

    @Override
    public void updateAnimations(float dt) {
        if(getAnimations() == null)
            return;
        for (SkillSprite animation : getAnimations()) {
            if (animation.getEntityState().equals(EntityState.INACTIVE))
                continue;

            animation.setPosition(getTarget());
            animation.setStateTimer(animation.getStateTimer() + dt);
            animation.update(dt);

            if (animation.isAnimationFinished()) {
                if (looping)
                    animation.clearTimers();
                else
                    resetSkill();
            }
        }
    }
}
