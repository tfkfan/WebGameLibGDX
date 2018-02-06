package com.webgame.game.world.skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public abstract class BuffSkill<T extends SkillSprite> extends Skill<T> {
    final int w = 100;
    final int h = 100;

    protected Vector2 vel = new Vector2(0, 0);

    public BuffSkill(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
        super(batch, spriteTexture, numFrames);
    }

    @Override
    protected void initFrame(T frame, Vector2 playerPosition, Vector2 targetPosition) {
        frame.setVelocity(vel);
        frame.setFinalAnimated(false);
        frame.setAnimateTimer(0);
        initPositions(frame);
    }

    protected void initPositions(T frame) {
        frame.updateDistance();
        frame.setAnimateTimer(0);
        frame.setActive(true);
    }

    @Override
    protected void updateFrame(T frame, float dt) {
        frame.setPosition(castingPlayer.getPosition().x - frame.getWidth()/2, castingPlayer.getPosition().y - frame.getHeight()/2);
        super.updateFrame(frame, dt);
        frame.updateDistance();
        frame.setAnimateTimer(frame.getAnimateTimer() + dt);
    }

    @Override
    protected void afterCustomAnimation() {
        if (getSkillState().isTimed())
            return;

        SkillSprite obj = getSkillObjects().get(0);
        if (obj.getAnimateTimer() > obj.getAnimationMaxDuration())
            getSkillState().setActive(false);
    }
}
