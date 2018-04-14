package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.Configs;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.MoveState;
import com.webgame.game.enums.SkillAnimationState;
import sun.security.krb5.Config;

public class SingleSkill extends Skill {
    public SingleSkill() {

    }

    public SingleSkill(Skill skill) {
        super(skill);
    }

    public SingleSkill(Player player) {
        super(player);
    }

    public SingleSkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation) {
        super(player, standTexture, gameAnimation);
    }

    public SingleSkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, Boolean looping) {
        super(player, standTexture, gameAnimation, looping);
    }

    public SingleSkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState) {
        super(player, standTexture, gameAnimation, animationState);
    }

    public SingleSkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState, Boolean looping) {
        super(player, standTexture, gameAnimation, animationState, looping);
    }

    public SingleSkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, Integer numFrames, SkillAnimationState animationState, Boolean looping) {
        super(player, standTexture, gameAnimation, numFrames, animationState, looping, null, null);
    }

    @Override
    public void cast(Vector2 target) {
        super.cast(target);

        for (SkillSprite animation : getAnimations()) {
            animation.setEntityState(EntityState.ACTIVE);
            animation.setMoveState(MoveState.MOVING);
        }
    }

    @Override
    public Skill createCopy() {
        return new SingleSkill(this);
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
