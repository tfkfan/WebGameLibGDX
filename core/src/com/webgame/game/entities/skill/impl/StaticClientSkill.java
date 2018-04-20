package com.webgame.game.entities.skill.impl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.Configs;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.skill.ClientSkill;
import com.webgame.game.entities.skill.SkillSprite;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.MoveState;
import com.webgame.game.enums.SkillAnimationState;
import com.webgame.game.server.entities.Player;
import com.webgame.game.server.entities.Skill;

public class StaticClientSkill extends ClientSkill {

    public StaticClientSkill() {
        super();
    }

    public StaticClientSkill(ClientSkill clientSkill) {
        super(clientSkill);
    }

    @Override
    public void initAnimation(SkillSprite animation) {
        animation.setSize(100 / Configs.PPM, 100 / Configs.PPM);
    }

    @Override
    public void cast(Vector2 target) {
        for (SkillSprite animation : getAnimations()) {
            animation.setEntityState(EntityState.ACTIVE);
            animation.setMoveState(MoveState.STATIC);
        }
    }

    @Override
    public <T extends Skill> T createCopy() {
        return (T) new StaticClientSkill(this);
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
