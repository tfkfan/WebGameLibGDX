package com.webgame.common.client.entities.skill.impl;


import com.badlogic.gdx.math.Vector2;
import com.webgame.common.client.entities.skill.ClientSkill;
import com.webgame.common.client.entities.skill.SkillSprite;
import com.webgame.common.client.enums.EntityState;
import com.webgame.common.client.enums.MoveState;

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
                if (getSpriteAttributes().isLooping())
                    animation.clearTimers();
                else
                    resetSkill();
            }
        }
    }
}
