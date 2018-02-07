package com.webgame.game.world.skills;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.entities.skill.SkillOrig;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public abstract class StaticAOESkillOrig<T extends SkillSprite> extends SkillOrig<T> {
    final int w = 100;
    final int h = 100;

    protected Vector2 vel = new Vector2(0, 0);

    public StaticAOESkillOrig(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
        super(batch, spriteTexture, numFrames);
        this.getSkillState().setAOE(true);
        this.getSkillState().setStatic(true);
        this.getSkillState().setArea(new Rectangle(0, 0, w / PPM, h / PPM));
    }

    @Override
    protected void initFrame(T frame, Vector2 playerPosition, Vector2 targetPosition) {
        frame.setVelocity(vel);
        frame.setFinalAnimated(false);
        frame.setAnimateTimer(0);
        getSkillState().getArea().setPosition(targetPosition.x - getSkillState().getArea().getWidth() / 2, targetPosition.y - getSkillState().getArea().getHeight() / 2);
        initPositions(frame);
    }

    protected void initPositions(T frame) {
        float x = getSkillState().getArea().getX() - (frame.getWidth() - getSkillState().getArea().getWidth()) / 2;
        float y = getSkillState().getArea().getY() - (frame.getHeight() - getSkillState().getArea().getHeight()) / 2;
        frame.updateDistance();
        frame.setAnimateTimer(0);
        frame.setActive(true);
        frame.setPosition(x, y);
    }

    @Override
    protected void updateFrame(T frame, float dt) {
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
