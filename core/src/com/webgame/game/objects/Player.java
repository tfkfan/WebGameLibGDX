package com.webgame.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.state.Direction;
import com.webgame.game.state.PlayerState;

public abstract class Player implements GameObject, Movable, Animated {
	protected Texture spriteTexture;
	protected Sprite sprite;
	protected Vector2 position;
	protected Vector2 velocity;
	protected float stateTimer;

	protected PlayerState currState;
	protected PlayerState prevState;
	
	protected Direction direction;
	protected Direction oldDirection;
	
	protected Double healthPoints;
	protected Double manaPoints;

	protected String playerName;
	protected Integer level;

	public Player(String spritePath) {
		position = new Vector2();
		velocity = new Vector2();

		spriteTexture = new Texture(Gdx.files.internal(spritePath));

		prevState = PlayerState.STAND;
		currState = PlayerState.STAND;
		
		direction = Direction.UP;
		oldDirection = Direction.UP;

		stateTimer = 0;

		sprite = new Sprite(spriteTexture, 20, 20, 50, 50);
		sprite.setPosition(10, 10);
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Double getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(Double healthPoints) {
		this.healthPoints = healthPoints;
	}

	public Double getManaPoints() {
		return manaPoints;
	}

	public void setManaPoints(Double manaPoints) {
		this.manaPoints = manaPoints;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Texture getSpriteTexture() {
		return spriteTexture;
	}

	public void setSpriteTexture(Texture spriteTexture) {
		this.spriteTexture = spriteTexture;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
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
		if (velocity.x != 0 || velocity.y != 0)
			return PlayerState.WALK;

		return PlayerState.STAND;
	}

	public abstract void draw(SpriteBatch batch, float dt);

	@Override
	public void move() {
		oldDirection = direction;
		
		if(velocity.x > 0)
			direction = Direction.RIGHT;
		else if(velocity.x < 0)
			direction = Direction.LEFT;
		
		if(velocity.y > 0)
			direction = Direction.UP;
		else if(velocity.y < 0)
			direction = Direction.DOWN;
		
		if(velocity.x > 0 && velocity.y > 0)
			direction = Direction.UPRIGHT;
		else if(velocity.x > 0 && velocity.y < 0)
			direction = Direction.RIGHTDOWN;
		else if(velocity.x < 0 && velocity.y > 0)
			direction = Direction.UPLEFT;
		else if(velocity.x < 0 && velocity.y < 0)
			direction = Direction.LEFTDOWN;
		
		if(oldDirection != direction || currState != prevState)
			stateTimer = 0;

		position.add(velocity);
	}
}
