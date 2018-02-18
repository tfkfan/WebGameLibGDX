package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.MoveState;
import com.webgame.game.enums.SkillAnimationState;
import com.webgame.game.skill_animations.SkillAnimation;

import java.util.ArrayList;
import java.util.List;

import static com.webgame.game.Configs.PPM;

public class FallingSkill extends AOESkill {
    protected float fallTimer;
    protected final float fallDuration = 10;
    protected int index;

    protected final float fallOffset = 1f / PPM;
    protected final Vector2 fallVelocity = new Vector2(4 / PPM, -8 / PPM);
    protected final Vector2 fallOffsetVec = new Vector2(fallVelocity.x * 10, (-fallVelocity.y) * 10);
    protected final float tmp = 0.1f;

    public FallingSkill(Player player, TextureRegion st, GameAnimation gameAnimation) {
        super(player);
        List<SkillAnimation> animations = new ArrayList<SkillAnimation>();
        for(int i = 0; i < 50; i++)
            animations.add(new SkillAnimation(st, gameAnimation));
        setAnimations(animations);
    }

    @Override
    protected void init(Player player) {
        super.init(player);
        index = 0;
        setVelocity(fallVelocity);


    }

    @Override
    public void clearTimers() {
        super.clearTimers();
        fallTimer = 0;
    }

    protected void initFrame(SkillAnimation frame) {
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
                SkillAnimation animation = animations.get(index);
                initFrame(animation);
                if (index < animations.size() - 1)
                    index++;
                else
                    index = -1;
                fallTimer = 0;
            }
        }


        for (SkillAnimation animation : animations) {
            if (animation.getEntityState().equals(EntityState.INACTIVE))
                continue;
            if (animation.getDistance().y > fallOffsetVec.y)
                animation.setMoveState(MoveState.STATIC);
            if(animation.getMoveState().equals(MoveState.STATIC)){
                animation.setStateTimer(animation.getStateTimer() + dt);
                if(animation.isAnimationFinished())
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
