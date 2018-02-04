package com.webgame.game.world.skills;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public abstract class StaticSkill<T extends SkillSprite> extends Skill<T> {
    protected float skillDuration = 10;

    protected Vector2 vel = new Vector2(0, 0);

    public StaticSkill(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
        super(batch, spriteTexture, numFrames);
        getSkillState().setStatic(true);
    }

    @Override
    protected void initFrame(T frame, Vector2 playerPosition, Vector2 targetPosition) {
        frame.setVelocity(vel);
        frame.setFinalAnimated(false);
        frame.setAnimateTimer(0);
        frame.setPosition(targetPosition.x, targetPosition.y);
        initPositions(frame, targetPosition);
    }

    protected void initPositions(T frame, Vector2 target) {
        float x = target.x - frame.getWidth() / 2;
        float y = target.y;
        frame.updateDistance();
        frame.setAnimateTimer(0);
        frame.setActive(true);
        frame.setPosition(x, y);
    }

    @Override
    protected void afterCustomAnimation() {
        T obj = this.getSkillObjects().get(0);
        if (obj.getAnimateTimer() > obj.getAnimationMaxDuration())
            getSkillState().setActive(false);
    }

    @Override
    protected void updateFrame(T frame, float dt) {
        super.updateFrame(frame, dt);

        frame.updateDistance();
        frame.setAnimateTimer(frame.getAnimateTimer() + dt);

        float x = frame.getX();
        float y = frame.getY();
        if (x < -10 || y < -10 || x > 10 || y > 10) {
            frame.setActive(false);
        }
    }
}
