package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.player.ClientPlayer;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.MoveState;
import com.webgame.game.enums.SkillAnimationState;

import static com.webgame.game.Configs.PPM;

public class FallingClientSkill extends AOEClientSkill {
    protected final static int numFrames = 50;

    protected float fallTimer;
    protected int index;

    protected final Vector2 fallVelocity = new Vector2(4 / PPM, -8 / PPM);
    protected final Vector2 fallOffsetVec = new Vector2(fallVelocity.x * 10, (-fallVelocity.y) * 10);
    protected final float tmp = 0.1f;

    public FallingClientSkill(){

    }

    public FallingClientSkill(ClientSkill clientSkill) {
        super(clientSkill);
    }

    public FallingClientSkill(ClientPlayer clientPlayer) {
        super(clientPlayer);
    }

    public FallingClientSkill(ClientPlayer clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation) {
        super(clientPlayer, standTexture, gameAnimation, numFrames, SkillAnimationState.FULL_ANIMATION, false,null, null);
    }

    public FallingClientSkill(ClientPlayer clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, float[] standSizes, float[] animSizes) {
        super(clientPlayer, standTexture, gameAnimation, numFrames, SkillAnimationState.FULL_ANIMATION, false, standSizes, animSizes);
    }

    @Override
    protected void init(ClientPlayer clientPlayer) {
        super.init(clientPlayer);
        index = 0;
        setVelocity(fallVelocity);
    }

    @Override
    public ClientSkill createCopy() {
        return new FallingClientSkill(this);
    }

    @Override
    public void clearTimers() {
        super.clearTimers();
        fallTimer = 0;
    }

    protected void initFrame(SkillSprite frame) {
        float x = getRandomPos(getArea().getX(), getArea().getX() + getArea().getWidth());
        float y = getRandomPos(getArea().getY(), getArea().getY() + getArea().getHeight());
        frame.init();
        frame.setEntityState(EntityState.ACTIVE);
        frame.setMoveState(MoveState.MOVING);
        frame.setVelocity(fallVelocity);
        frame.setPosition(x - fallOffsetVec.x - frame.getXOffset(), y + fallOffsetVec.y - frame.getYOffset());
    }

    @Override
    public void updateAnimations(float dt) {
        if (index != -1) {
            fallTimer += dt;
            if (fallTimer >= tmp) {
                SkillSprite animation = animations.get(index);
                initFrame(animation);
                if (index < animations.size() - 1)
                    index++;
                else
                    index = -1;
                fallTimer = 0;
            }
        }


        for (SkillSprite animation : animations) {
            if (animation.getEntityState().equals(EntityState.INACTIVE))
                continue;
            if (animation.getDistance().y > fallOffsetVec.y)
                animation.setMoveState(MoveState.STATIC);
            if (animation.getMoveState().equals(MoveState.STATIC)) {
                animation.setStateTimer(animation.getStateTimer() + dt);
                if (animation.isAnimationFinished())
                    initFrame(animation);
            }


            animation.update(dt);
            float x = animation.getPosition().x;
            float y = animation.getPosition().y;
            if (x < -50 || y < -50 || x > 50 || y > 50) {
                animation.setEntityState(EntityState.INACTIVE);
            }
        }


        if (fallTimer >= tmp)
            fallTimer = 0;
    }

    public float getRandomPos(float min, float max) {
        Double random = min + Math.random() * (max - min);
        return random.floatValue();
    }


}