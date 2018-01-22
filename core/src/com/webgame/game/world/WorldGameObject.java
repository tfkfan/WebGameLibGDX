package com.webgame.game.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.webgame.game.utils.SpriteTextureLoader;
import com.webgame.game.world.GameObject;

public abstract class WorldGameObject extends Actor {
    protected World world;
    protected Body b2body;
    protected Vector2 velocity;

    protected float xOffset;
    protected float yOffset;

    public WorldGameObject() {

    }

    public WorldGameObject(World world) {
        setWorld(world);
        createObject(world);
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

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getXOffset() {
        return xOffset;
    }

    public void setXOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    public void setYOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
       batch.draw(getFrame(), getX(), getY(), getWidth(), getHeight());
    }

    public void update(float dt) {
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x, b2body.getPosition().y);
    }

    public abstract void createObject(World world);
    protected abstract TextureRegion getFrame();
}
