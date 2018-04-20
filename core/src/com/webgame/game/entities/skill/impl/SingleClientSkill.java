package com.webgame.game.entities.skill.impl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.skill.ClientSkill;
import com.webgame.game.entities.skill.SkillSprite;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.MoveState;
import com.webgame.game.enums.SkillAnimationState;
import com.webgame.game.server.entities.Player;

public class SingleClientSkill extends ClientSkill {
    public SingleClientSkill() {

    }

    public SingleClientSkill(ClientSkill clientSkill) {
        super(clientSkill);
    }

    @Override
    public void cast(Vector2 target) {
        for (SkillSprite animation : getAnimations()) {
            animation.setEntityState(EntityState.ACTIVE);
            animation.setMoveState(MoveState.MOVING);
        }
    }

    @Override
    public SingleClientSkill createCopy() {
        return new SingleClientSkill(this);
    }

    @Override
    public void setMoveState(MoveState moveState){
        super.setMoveState(moveState);
        if(getAnimations() == null)
            return;

        for (SkillSprite animation : getAnimations()) {
            animation.setMoveState(moveState);
        }
    }

    @Override
    public void updateAnimations(float dt) {
        for (SkillSprite animation : getAnimations()) {
            animation.setPosition(getPosition());
            if (getMoveState().equals(MoveState.STATIC)) {
                animation.setMoveState(MoveState.STATIC);
                if (!animation.isAnimationFinished()) {
                    animation.setStateTimer(animation.getStateTimer() + dt);
                } else {
                    resetSkill();
                    return;
                }
            }
        }
    }
}
