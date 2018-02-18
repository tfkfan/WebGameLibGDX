package com.webgame.game.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.*;
import com.webgame.game.entities.Entity;

import static com.webgame.game.Configs.PPM;

public abstract class WorldEntity extends AnimatedEntity {
    transient protected World world;
    transient protected Body b2body;
    protected float defaultRadius = 20 / PPM;
    protected Circle objectShape;

    public WorldEntity() {
        init();
    }

    public WorldEntity(World world) {
        setWorld(world);
        createObject(world);
        init();
    }

    protected void init() {
        this.setPosition(0, 0);
        setBounds(getPosition().x, getPosition().y, 100 / PPM, 100 / PPM);
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

        shape.setRadius(defaultRadius);

        objectShape = new Circle(0, 0, getRadius());
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public Circle getShape() {
        return objectShape;
    }

    public void setShape(Circle shape) {
        this.objectShape = shape;
    }

    public float getRadius() {
        return defaultRadius;
    }

    public void setRadius(float radius) {
        defaultRadius = radius;
    }
}
