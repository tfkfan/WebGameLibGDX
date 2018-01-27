package com.webgame.game.world;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.*;

import static com.webgame.game.Configs.PPM;

public abstract class WorldGameObject extends GameActor {
    protected World world;
    protected Body b2body;
    protected float defaultRadius = 20/PPM;

    public WorldGameObject() {
        init();
    }

    public WorldGameObject(World world) {
        setWorld(world);
        createObject(world);
        init();
    }

    @Override
    protected void init(){
        super.init();
        this.setPosition(0,0);
        setBounds(getX(), getX(),100/PPM,100/PPM);
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


    public void update(float dt) {
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x, b2body.getPosition().y);
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

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public float getRadius(){
        return defaultRadius;
    }

    public void setRadius(float radius){
        defaultRadius = radius;
    }
}
