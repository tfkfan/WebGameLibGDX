package com.webgame.game.world.skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

import static com.webgame.game.Configs.PPM;

public abstract class FallingAOESkill<T extends SkillSprite> extends Skill<T> {
    protected float fallTimer;
    protected final float fallDuration = 10;
    protected int index;

    protected final float fallOffset = 1f / PPM;
    protected final Vector2 fallVelocity = new Vector2(4 / PPM, -8 / PPM);
    protected final Vector2 fallOffsetVec = new Vector2(fallVelocity.x * 10, (-fallVelocity.y) * 10);
    protected final float tmp = 0.1f;

    public FallingAOESkill(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
        super(batch, spriteTexture, numFrames);
        fallTimer = 0;
        index = 0;
        setAOE(true);
        setFalling(true);
        setTimed(true);
        setDamage(2d);
        this.setArea(new Rectangle(0, 0, 100 / PPM, 100 / PPM));
    }

    @Override
    public void clearTimers() {
        super.clearTimers();
        fallTimer = 0;
    }

    @Override
    protected void resetSkill() {
        super.resetSkill();
        fallTimer = 0;
        index = 0;
    }

    @Override
    public void customAnimation(float dt) {
        if (!isActive)
            return;

        skillTimer += dt;

        if (index != -1) {
            fallTimer += dt;
            if (fallTimer >= tmp) {
                T obj = skillObjects.get(index);
                obj.updateDistance();
                obj.setActive(true);

                if (index < numFrames - 1)
                    index++;
                else
                    index = -1;
                fallTimer = 0;
            }
        }

        if (fallTimer >= tmp)
            fallTimer = 0;
    }

    @Override
    protected void initFrame(T frame, Vector2 playerPosition, Vector2 targetPosition) {
        frame.setVelocity(fallVelocity);
        frame.setFinalAnimated(false);

        area.setPosition(targetPosition.x - area.getWidth() / 2, targetPosition.y - area.getHeight() / 2);
        initPositions(frame);
    }

    protected void initPositions(T frame) {
        float x = getRandomPos(area.getX(), area.getX() + area.getWidth());
        float y = getRandomPos(area.getY(), area.getY() + area.getHeight());
        frame.updateDistance();
        frame.setMarked(false);
        frame.setPosition(x - fallOffsetVec.x - frame.getXOffset(), y + fallOffsetVec.y - frame.getYOffset());
    }

    @Override
    protected void updateFrame(T frame, float dt) {
        super.updateFrame(frame, dt);

        if (frame.getDistance().y > fallOffsetVec.y) {
            if (!frame.isFinalAnimated()) {
                frame.setStatic(true);
            }
            if (frame.isFinalAnimated() && frame.isStatic()) {
                frame.setFinalAnimated(false);
                frame.setStatic(false);
                initPositions(frame);
            }
        }

        if (!frame.isFinalAnimated() && frame.isStatic()) {
            frame.setAnimateTimer(frame.getAnimateTimer() + dt);
            if (frame.getAnimateTimer() > frame.getAnimationMaxDuration()) {
                frame.setFinalAnimated(true);
                frame.setAnimateTimer(0);
            }
        }

        float x = frame.getX();
        float y = frame.getY();
        if (x < -10 || y < -10 || x > 10 || y > 10) {
            frame.setActive(false);
        }
    }
}
