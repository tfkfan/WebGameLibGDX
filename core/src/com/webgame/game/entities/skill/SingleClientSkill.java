package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.player.ClientPlayer;
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

    public SingleClientSkill(Player clientPlayer) {
        super(clientPlayer);
    }

    public SingleClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation) {
        super(clientPlayer, standTexture, gameAnimation);
    }

    public SingleClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, Boolean looping) {
        super(clientPlayer, standTexture, gameAnimation, looping);
    }

    public SingleClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState) {
        super(clientPlayer, standTexture, gameAnimation, animationState);
    }

    public SingleClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState, Boolean looping) {
        super(clientPlayer, standTexture, gameAnimation, animationState, looping);
    }

    public SingleClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, Integer numFrames, SkillAnimationState animationState, Boolean looping) {
        super(clientPlayer, standTexture, gameAnimation, numFrames, animationState, looping, null, null);
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
    public ClientSkill createCopy() {
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
