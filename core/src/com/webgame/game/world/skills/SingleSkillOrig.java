package com.webgame.game.world.skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.entities.skill.SkillOrig;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public abstract class SingleSkillOrig<T extends SkillSprite> extends SkillOrig<T> {
    public SingleSkillOrig(SpriteBatch batch, Texture spriteTexture) throws Exception {
        super(batch, spriteTexture, 1);
        getSkillState().setAOE(false);

    }

    @Override
    protected void initFrame(T frame, Vector2 playerPosition, Vector2 targetPosition) {
        Vector2 vel = new Vector2(targetPosition.x - playerPosition.x, targetPosition.y - playerPosition.y);
        float len = vel.len();
        vel.set(absVelocity * vel.x / len, absVelocity * vel.y / len);

        frame.setVelocity(vel);
        frame.setFinalAnimated(false);
        frame.setActive(true);
        frame.setPosition(playerPosition.x, playerPosition.y);
    }


    @Override
    protected void updateFrame(T frame, float dt) {
        super.updateFrame(frame, dt);

        if (frame.isMarked()) {
            if (!frame.isFinalAnimated()) {
                frame.setStatic(true);
            }
            if (frame.isFinalAnimated() && frame.isStatic()) {
                frame.setFinalAnimated(false);
                frame.setStatic(false);
            }
        }

        if (!frame.isFinalAnimated() && frame.isStatic()) {
            frame.setAnimateTimer(frame.getAnimateTimer() + dt);
            if (frame.getAnimateTimer() > frame.getAnimationMaxDuration()) {
                frame.setAnimateTimer(0);
                resetSkill();
            }
        }
    }
}