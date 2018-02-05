package com.webgame.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.webgame.game.world.common.IFramed;
import java.io.Serializable;

public abstract class Entity extends Group implements IFramed, Serializable {
    protected Long id;

    protected Vector2 velocity;
    protected float xOffset;
    protected float yOffset;

    public Entity() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getFrame(), getX() - getXOffset(), getY() - getYOffset(), getWidth(), getHeight());
    }
}
