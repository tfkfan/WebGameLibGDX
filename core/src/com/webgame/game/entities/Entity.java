package com.webgame.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.webgame.game.world.common.IFramed;

public abstract class Entity extends Group implements IFramed {
    protected Long id;

    protected Vector2 velocity;
    protected float xOffset;
    protected float yOffset;
    protected static final int dirs = 8;

    public Entity() {
        init();
    }

    protected void init() {
        velocity = new Vector2(0, 0);
        xOffset = yOffset = 0;
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
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getFrame(), getX() - getXOffset(), getY() - getYOffset(), getWidth(), getHeight());
    }
}
