package com.webgame.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.webgame.game.entities.attributes.PlayerAttributes;
import com.webgame.game.enums.Direction;
import com.webgame.game.enums.PlayerState;
import com.webgame.game.enums.State;
import com.webgame.game.world.common.IUpdatable;

import static com.webgame.game.Configs.PPM;

public abstract class Player extends WorldEntity implements IUpdatable{
    protected boolean isAlive;

    protected float stateTimer;

    protected Direction direction;
    protected Direction oldDirection;

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

        direction = Direction.UP;
        oldDirection = direction;

        currState = PlayerState.STAND;
        prevState = currState;

        getAttributes().setHealthPoints(1000);
        getAttributes().setMaxHealthPoints(1000);

        clearTimers();
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

    public Direction getOldDirection() {
        return oldDirection;
    }

    public void setOldDirection(Direction oldDirection) {
        this.oldDirection = oldDirection;
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

    public void setState(State state) {
        this.currState = state;
    }

    public State getState() {
        return currState;
    }

    public State getPrevState() {
        return prevState;
    }

    public void setPrevState(State prevState) {
        this.prevState = prevState;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (getB2body() != null)
            getB2body().setTransform(x, y, 0);
    }

    public TextureRegion[] getStandRegions() {
        return standRegions;
    }

    public void setStandRegions(TextureRegion[] standRegions) {
        this.standRegions = standRegions;
    }


    public boolean isAttackFinished(){
        Integer index = direction.getDirIndex();
        return attackAnimations.get(index).isAnimationFinished(stateTimer);
    }

    public void clearTimers(){
        stateTimer = 0;
    }

    @Override
    public TextureRegion getFrame() {
        TextureRegion region = null;

        PlayerState currState = (PlayerState) getState();

        Integer index = direction.getDirIndex();

        switch (currState) {
            case WALK:
                region = animations.get(index).getKeyFrame(stateTimer, true);
                break;
            case ATTACK:
                region = attackAnimations.get(index).getKeyFrame(stateTimer, false);
                break;
            case STAND:
            default:
                region = standRegions[this.direction.getDirIndex()];
                break;
        }

        return region;
    }

    @Override
    public void update(float dt){
        if(!getState().equals(PlayerState.ATTACK)
                || getState().equals(PlayerState.ATTACK) && isAttackFinished()) {
            if (getVelocity().isZero())
                setState(PlayerState.STAND);
            else
                setState(PlayerState.WALK);
        }

        stateTimer += dt;

        if (stateTimer >= 10)
            clearTimers();
    }
}
