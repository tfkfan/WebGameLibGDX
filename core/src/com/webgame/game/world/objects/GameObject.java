package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.state.Direction;
import com.webgame.game.state.PlayerState;
import com.webgame.game.state.State;
import com.webgame.game.world.objects.impl.SpriteTextureLoader;
import static com.webgame.game.Configs.PPM;

public abstract class GameObject extends Sprite implements Movable, Animated {
	protected Vector2 currVel;

	protected float stateTimer;

	protected State currState;
	protected State prevState;

	protected Direction direction;
	protected Direction oldDirection;

	protected float xOffset;
	protected float yOffset;

	protected final int dirs = 8;

	protected SpriteTextureLoader sTextureLoader;

	public GameObject(){
		init();
	}
	
	public GameObject(String spritePath) {
		loadTexture(null, spritePath);
		init();
	}

	public GameObject(SpriteBatch batch) {
		loadTexture(batch, null);
		init();
	}

	public GameObject(SpriteBatch batch, String spritePath) {
		loadTexture(batch, spritePath);
		init();
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

	protected void loadTexture(SpriteBatch batch, String spritePath) {
		sTextureLoader = new SpriteTextureLoader();
		if (batch != null)
			sTextureLoader.setSpriteBatch(batch);
		if (spritePath != null)
			sTextureLoader.loadSprite(spritePath);
	}

	public SpriteTextureLoader getSpriteTextureLoader() {
		return sTextureLoader;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
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

	public State getCurrState() {
		return currState;
	}

	public void setCurrState(State currState) {
		this.currState = currState;
	}

	public State getPrevState() {
		return prevState;
	}

	public void setPrevState(State prevState) {
		this.prevState = prevState;
	}

	protected void updateStateTimer(float dt) {
		stateTimer += dt;
	}

	public State getState() {
		if (currVel.x != 0 || currVel.y != 0)
			return PlayerState.WALK;

		return PlayerState.STAND;
	}

	public Integer getDirectionIndex() {
		Integer index = 0;
		switch (direction) {
		case UP:
			index = 0;
			break;
		case UPRIGHT:
			index = 1;
			break;
		case RIGHT:
			index = 2;
			break;
		case RIGHTDOWN:
			index = 3;
			break;
		case DOWN:
			index = 4;
			break;
		case LEFTDOWN:
			index = 5;
			break;
		case LEFT:
			index = 6;
			break;
		case UPLEFT:
			index = 7;
			break;
		default:
			index = 0;
			break;
		}
		return index;
	}

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

		
		updateStateTimer(dt);
		setRegion(getFrame());
	}
}
