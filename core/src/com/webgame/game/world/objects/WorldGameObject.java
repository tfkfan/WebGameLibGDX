package com.webgame.game.world.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class WorldGameObject extends GameObject {
	protected World world;
	protected Body b2body;

	public WorldGameObject() {
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

	@Override
	public void move(float dt) {
		b2body.setLinearVelocity(velocity);
		setPosition(b2body.getPosition().x - xOffset, b2body.getPosition().y - yOffset);
		super.move(dt);
	}

	public abstract void createObject(World world);
}
