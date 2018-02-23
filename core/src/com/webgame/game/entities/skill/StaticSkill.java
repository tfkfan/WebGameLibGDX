package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.Configs;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.SkillAnimationState;

public class StaticSkill extends Skill {

    public StaticSkill(StaticSkill skill){
        super(skill);
    }

    public StaticSkill(Player player) {
        super(player);
    }

    public StaticSkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation) {
        super(player, standTexture, gameAnimation);
    }

    public StaticSkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, Boolean looping) {
        super(player, standTexture, gameAnimation, looping);
    }

    public StaticSkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState) {
        super(player, standTexture, gameAnimation, animationState);
    }

    public StaticSkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState, Boolean looping) {
        super(player, standTexture, gameAnimation, animationState, looping);
    }

    public StaticSkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, Integer numFrames, SkillAnimationState animationState, Boolean looping) {
        super(player, standTexture, gameAnimation, numFrames, animationState, looping);
    }

    @Override
    public void initAnimation(SkillSprite animation) {
        animation.setSize(100 / Configs.PPM, 100 / Configs.PPM);
    }

    @Override
    public void cast(Vector2 target) {
        super.cast(target);
        for (SkillSprite animation : getAnimations()) {
            animation.setEntityState(EntityState.ACTIVE);
        }
    }

    @Override
    public void updateAnimations(float dt) {
        for (SkillSprite animation : getAnimations()) {
            if (animation.getEntityState().equals(EntityState.INACTIVE))
                continue;

            animation.setPosition(getTarget());
            animation.setStateTimer(animation.getStateTimer() + dt);
            if (animation.isAnimationFinished())
                resetSkill();

            animation.update(dt);
            float x = animation.getPosition().x;
            float y = animation.getPosition().y;
            if (x < -50 || y < -50 || x > 50 || y > 50) {
                animation.setEntityState(EntityState.INACTIVE);
            }
        }
    }
}
