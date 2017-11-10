package com.webgame.game.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.webgame.game.state.Direction;
import com.webgame.game.state.PlayerState;
import static com.webgame.game.Configs.PPM;

public abstract class GameObject extends Sprite implements Movable, Animated {
	protected World world;
	protected Body b2body;

	protected Texture spriteTexture;
	protected Vector2 currVel;

	protected float stateTimer;

	protected PlayerState currState;
	protected PlayerState prevState;

	protected Direction direction;
	protected Direction oldDirection;

	protected float xOffset;
	protected float yOffset;

	protected SpriteBatch batch;
	
	protected final int dirs = 8;

	public GameObject(String spritePath) {
		init();
		loadSprite(spritePath);
	}

	public GameObject(SpriteBatch batch) {
		setSpriteBatch(batch);
		init();
	}

	public GameObject(SpriteBatch batch, String spritePath) {
		setSpriteBatch(batch);
		init();
		loadSprite(spritePath);
	}

	public void init() {
		xOffset = yOffset = 0;

		currVel = new Vector2();

		prevState = PlayerState.STAND;
		currState = PlayerState.STAND;

		direction = Direction.UP;
		oldDirection = Direction.UP;

		stateTimer = 0;

		this.setBounds(0, 0, 60 / PPM, 60 / PPM);
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

	public void loadSprite(String spritePath) {
		spriteTexture = new Texture(Gdx.files.internal(spritePath));
	}

	public void setSpriteBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Texture getSpriteTexture() {
		return spriteTexture;
	}

	public void setSpriteTexture(Texture spriteTexture) {
		this.spriteTexture = spriteTexture;
	}

	public Vector2 getVelocity() {
		return currVel;
	}

	public void setVelocity(Vector2 velocity) {
		this.currVel = velocity;
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

	public float getStateTimer() {
		return stateTimer;
	}

	public void setStateTimer(float stateTimer) {
		this.stateTimer = stateTimer;
	}

	public PlayerState getCurrState() {
		return currState;
	}

	public void setCurrState(PlayerState currState) {
		this.currState = currState;
	}

	public PlayerState getPrevState() {
		return prevState;
	}

	public void setPrevState(PlayerState prevState) {
		this.prevState = prevState;
	}

	protected void updateStateTimer(float dt) {
		stateTimer += dt;
	}

	public PlayerState getState() {
		if (currVel.x != 0 || currVel.y != 0)
			return PlayerState.WALK;

		return PlayerState.STAND;
	}

	public abstract void createObject(World world);

	@Override
	public void move(float dt) {
		oldDirection = direction;

		if (currVel.x > 0)
			direction = Direction.RIGHT;
		else if (currVel.x < 0)
			direction = Direction.LEFT;

		if (currVel.y > 0)
			direction = Direction.UP;
		else if (currVel.y < 0)
			direction = Direction.DOWN;

		if (currVel.x > 0 && currVel.y > 0)
			direction = Direction.UPRIGHT;
		else if (currVel.x > 0 && currVel.y < 0)
			direction = Direction.RIGHTDOWN;
		else if (currVel.x < 0 && currVel.y > 0)
			direction = Direction.UPLEFT;
		else if (currVel.x < 0 && currVel.y < 0)
			direction = Direction.LEFTDOWN;

		if (oldDirection != direction || currState != prevState)
			stateTimer = 0;

		b2body.setLinearVelocity(currVel);
		setPosition(b2body.getPosition().x - xOffset, b2body.getPosition().y - yOffset);

		updateStateTimer(dt);
		setRegion(getFrame());
	}
}
