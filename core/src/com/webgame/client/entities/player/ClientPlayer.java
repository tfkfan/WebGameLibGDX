package com.webgame.client.entities.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.webgame.client.enums.*;
import com.webgame.client.world.common.IUpdatable;
import com.webgame.server.entities.Player;

public abstract class ClientPlayer extends Player implements IUpdatable {
    protected transient float stateTimer;
    protected transient float attackTimer;

    protected transient Array<Animation<TextureRegion>> animations;
    protected transient Array<Animation<TextureRegion>> attackAnimations;
    protected transient TextureRegion[] standRegions;

    protected transient World world;
    protected transient Body b2body;

    public ClientPlayer() {
        super();
        clearTimers();
    }

    public abstract void initPlayer();

    public Body getB2body() {
        return b2body;
    }

    public void setB2body(Body b2body) {
        this.b2body = b2body;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void applyVelocity() {
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition());
    }

    public void createObject(World world) {
        setWorld(world);

        BodyDef bdef = new BodyDef();
        bdef.position.set(0, 0);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(getRadius());

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (getB2body() != null)
            getB2body().setTransform(x, y, 0);
    }

    public boolean isAttackFinished() {
        Integer index = directionState.getDirIndex();
        return attackAnimations.get(index).isAnimationFinished(attackTimer);
    }

    public void clearTimers() {
        stateTimer = 0;
        attackTimer = 0;
    }

    @Override
    public TextureRegion getFrame() {
        TextureRegion region;

        PlayerMoveState currState = getCurrAnimationState();
        PlayerAttackState attackState = getCurrentAttackState();

        Integer index = directionState.getDirIndex();

        if (attackState.equals(PlayerAttackState.BATTLE))
            region = attackAnimations.get(index).getKeyFrame(attackTimer, false);
        else {
            switch (currState) {
                case WALK:
                    region = animations.get(index).getKeyFrame(stateTimer, true);
                    break;
                case STAND:
                default:
                    region = standRegions[this.directionState.getDirIndex()];
                    break;
            }
        }

        return region;
    }

    @Override
    public void update(float dt) {
        if (getVelocity().isZero())
            setCurrAnimationState(PlayerMoveState.STAND);
        else
            setCurrAnimationState(PlayerMoveState.WALK);

        if (getCurrentAttackState().equals(PlayerAttackState.BATTLE)) {
            if (isAttackFinished()) {
                setCurrentAttackState(PlayerAttackState.SAFE);
                attackTimer = 0;
            } else
                attackTimer += dt;
        }

        stateTimer += dt;

        if (stateTimer >= 10)
            clearTimers();
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

    public TextureRegion[] getStandRegions() {
        return standRegions;
    }

    public void setStandRegions(TextureRegion[] standRegions) {
        this.standRegions = standRegions;
    }
}
