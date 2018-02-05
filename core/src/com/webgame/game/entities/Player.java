package com.webgame.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.webgame.game.entities.attributes.PlayerAttributes;
import com.webgame.game.enums.Direction;
import com.webgame.game.enums.PlayerState;
import com.webgame.game.enums.State;

import static com.webgame.game.Configs.PPM;

public abstract class Player extends WorldEntity {
    protected boolean isAnimated;
    protected boolean attackAnimation;
    protected boolean isAlive;
    protected float attackTimer;
    protected float stateTimer;
    protected final float attackLimit = 0.8f;

    protected Direction direction;

    protected State currState;
    protected State prevState;

    protected Array<Animation<TextureRegion>> animations;
    protected Array<Animation<TextureRegion>> attackAnimations;
    protected TextureRegion[] standRegions;

    protected PlayerAttributes attributes;

    public Player() {
        super();
    }

    @Override
    public void init() {
        super.init();
        attributes = new PlayerAttributes();

        isAlive = true;
        isAnimated = false;
        attackAnimation = false;

        direction = Direction.UP;

        currState = PlayerState.STAND;
        prevState = currState;

        getAttributes().setHealthPoints(1000);
        getAttributes().setMaxHealthPoints(1000);

        stateTimer = attackTimer = 0;
        setBounds(0, 0, 60 / PPM, 60 / PPM);
    }

    public PlayerAttributes getAttributes() {
        return attributes;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Array<Animation<TextureRegion>> getAnimations() {
        return animations;
    }

    public void setAnimations(Array<Animation<TextureRegion>> animations) {
        this.animations = animations;
    }

    public Array<Animation<TextureRegion>> getAttackAnimations() {
        return attackAnimations;
    }

    public void setAttackAnimations(Array<Animation<TextureRegion>> attackAnimations) {
        this.attackAnimations = attackAnimations;
    }

    @Override
    public void setPosition(float x, float y){
        super.setPosition(x, y);
        if( getB2body() != null)
            getB2body().setTransform(x, y, 0);
    }

    public TextureRegion[] getStandRegions() {
        return standRegions;
    }

    public void setStandRegions(TextureRegion[] standRegions) {
        this.standRegions = standRegions;
    }


    @Override
    public TextureRegion getFrame() {
        TextureRegion region = null;

        /*
        State currState = getState();

        Integer index = getDirectionIndex();

        switch (currState) {
            case WALK:
                region = animations.get(index).getKeyFrame(stateTimer, true);
                break;
            case ATTACK:
                region = attackAnimations.get(index).getKeyFrame(stateTimer, false);
                break;
            case STAND:
            default:
            */
        region = standRegions[this.direction.getDirIndex()];
        //        break;
        // }

        return region;
    }

    public float getStateTimer() {
        return stateTimer;
    }
}
