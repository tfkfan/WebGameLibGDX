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
    protected static final float absVel = 15;
    protected static final float dl = 0.1f;

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

        Vector2 playerPos = getPlayer().getPosition();
        Vector2 vel = new Vector2(target.x - playerPos.x, target.y - playerPos.y);
        vel.nor();
        vel.scl(absVel/ Configs.PPM);
        setVelocity(vel);
        setPosition(new Vector2(playerPos));

        for (SkillSprite animation : getAnimations()) {
            animation.setEntityState(EntityState.ACTIVE);
            animation.setMoveState(MoveState.MOVING);
        }
    }

    @Override
    public Skill createSkill() {
        return new SingleSkill(this);
    }

    @Override
    public void updateAnimations(float dt) {
        if(getMoveState().equals(MoveState.MOVING))
         getPosition().add(getVelocity());

        if(isCollided(getPosition(), getTarget())){
            setMoveState(MoveState.STATIC);

        }

        for (SkillSprite animation : getAnimations()) {
            animation.setPosition(getPosition());
            if(getMoveState().equals(MoveState.STATIC)){
                animation.setMoveState(MoveState.STATIC);
                if(!animation.isAnimationFinished()) {
                    animation.setStateTimer(animation.getStateTimer() + dt);
                }else {
                    resetSkill();
                    return;
                }
            }
        }
    }

    protected boolean isCollided(Vector2 skillPos, Vector2 target){
        if(skillPos.x >= target.x - dl && skillPos.x <= target.x + dl &&
                skillPos.y >= target.y - dl && skillPos.y <= target.y + dl)
            return true;
        return false;
    }
}
