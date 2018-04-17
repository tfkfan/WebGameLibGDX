package com.webgame.game.entities.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.webgame.game.Configs;
import com.webgame.game.entities.player.impl.Mage;
import com.webgame.game.enums.*;
import com.webgame.game.server.entities.Player;
import com.webgame.game.server.entities.Skill;
import com.webgame.game.world.common.IUpdatable;

public abstract class ClientPlayer extends Player implements IUpdatable {
    protected transient float stateTimer;
    protected transient float attackTimer;

    protected transient Array<Animation<TextureRegion>> animations;
    protected transient Array<Animation<TextureRegion>> attackAnimations;
    protected transient TextureRegion[] standRegions;

    transient protected World world;
    transient protected Body b2body;

    public ClientPlayer() {
        super();
        clearTimers();
    }

    public static ClientPlayer createPlayer(World world) {
        ClientPlayer clientPlayer = new Mage(Configs.PLAYERSHEETS_FOLDER + "/mage.png");
        clientPlayer.createObject(world);
        return clientPlayer;
    }

    public Skill castSkill(Vector2 target, String id) {
        Skill currSkill = getCurrentSkill();
        if (currSkill == null)
            return null;

        // Long end = getCurrentSkill().getStart() + getCurrentSkill().getCooldown();
        //Long currentTime = System.currentTimeMillis();

        //if (currentTime < end)
        //   return null;

        //  currSkill.setStart(System.currentTimeMillis());

       // final ClientSkill skill = currSkill.createCopy();
        //skill.cast(target);
       // activeSkills.put(id, skill);
        return null;
    }

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

        setShape(new Circle(0, 0, getRadius()));
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
