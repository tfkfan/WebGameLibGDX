package com.webgame.game.server.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.enums.EntityState;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected String id;
    protected String name;

    protected Integer level;

    protected Vector2 position;
    protected Vector2 velocity;
    protected float xOffset;
    protected float yOffset;

    protected float width;
    protected float height;

    protected EntityState entityState;

    public Entity(){
        velocity = new Vector2(0, 0);
        position = new Vector2(0, 0);
        xOffset = yOffset = 0;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public abstract void draw(Batch batch, float parentAlpha);
}
