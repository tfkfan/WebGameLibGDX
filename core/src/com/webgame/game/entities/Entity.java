package com.webgame.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.webgame.game.enums.EntityState;
import com.webgame.game.world.common.IFramed;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected Long id;

    protected Vector2 position;
    protected Vector2 velocity;
    protected float xOffset;
    protected float yOffset;

    protected float width;
    protected float height;

    protected EntityState entityState;

    public Entity() {
        velocity = new Vector2(0, 0);
        position = new Vector2(0, 0);
        xOffset = yOffset = 0;
    }

    public EntityState getEntityState() {
        return entityState;
    }

    public void setEntityState(EntityState entityState) {
        this.entityState = entityState;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setSize(float width, float height) {
        setWidth(width);
        setHeight(height);
    }

    public void setBounds(float x, float y, float width, float height) {
        setSize(width, height);
        setPosition(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        setPosition(new Vector2(x, y));
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

    public abstract void draw(Batch batch, float parentAlpha);
}
