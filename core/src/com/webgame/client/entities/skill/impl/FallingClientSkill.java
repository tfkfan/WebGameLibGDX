package com.webgame.client.entities.skill.impl;

import com.badlogic.gdx.math.Vector2;
import com.webgame.client.entities.skill.ClientSkill;
import com.webgame.client.entities.skill.SkillSprite;
import com.webgame.client.enums.EntityState;
import com.webgame.client.enums.MoveState;
import com.webgame.server.entities.Player;

import static com.webgame.client.Configs.PPM;

public class FallingClientSkill extends AOEClientSkill {
    protected final static int numFrames = 50;

    protected float fallTimer;
    protected int index;

    protected final Vector2 fallVelocity = new Vector2(4 / PPM, -8 / PPM);
    protected final Vector2 fallOffsetVec = new Vector2(fallVelocity.x * 10, (-fallVelocity.y) * 10);
    protected final float tmp = 0.1f;

    public FallingClientSkill(){
        super();
    }

    public FallingClientSkill(ClientSkill clientSkill) {
        super(clientSkill);
    }

    @Override
    public void init(Player clientPlayer) {
        super.init(clientPlayer);
        index = 0;
        setVelocity(fallVelocity);
    }

    @Override
    public void cast(Vector2 targetPosition) {

    }

    @Override
    public FallingClientSkill createCopy() {
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
        if(getAnimations() == null)
            return;

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
