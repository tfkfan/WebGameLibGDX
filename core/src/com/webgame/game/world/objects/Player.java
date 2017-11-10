package com.webgame.game.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.webgame.game.state.Direction;
import com.webgame.game.state.PlayerState;

public abstract class Player implements GameObject, Movable, Animated {
	protected World world;
	public Body b2body;
	
	
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

	protected SpriteBatch batch;

	public Player(String spritePath) {
		init();
		loadSprite(spritePath);
	}

	public Player(SpriteBatch batch) {
		setSpriteBatch(batch);
		init();
	}

	public Player(SpriteBatch batch, String spritePath) {
		setSpriteBatch(batch);
		init();
		loadSprite(spritePath);
	}

	public void init() {
		position = new Vector2();
		velocity = new Vector2();

		prevState = PlayerState.STAND;
		currState = PlayerState.STAND;

		direction = Direction.UP;
		oldDirection = Direction.UP;

		stateTimer = 0;
	}
	
	public void definePlayer(World world) {
		this.world = world;
		
		BodyDef bdef = new BodyDef();
		bdef.position.set(position);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		
		shape.setRadius(20);
		
		fdef.shape = shape;
		b2body.createFixture(fdef);	
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public void loadSprite(String spritePath) {
		spriteTexture = new Texture(Gdx.files.internal(spritePath));
		sprite = new Sprite(spriteTexture, 20, 20, 50, 50);
		sprite.setPosition(10, 10);
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

	@Override
	public void move() {
		oldDirection = direction;

		if (velocity.x > 0)
			direction = Direction.RIGHT;
		else if (velocity.x < 0)
			direction = Direction.LEFT;

		if (velocity.y > 0)
			direction = Direction.UP;
		else if (velocity.y < 0)
			direction = Direction.DOWN;

		if (velocity.x > 0 && velocity.y > 0)
			direction = Direction.UPRIGHT;
		else if (velocity.x > 0 && velocity.y < 0)
			direction = Direction.RIGHTDOWN;
		else if (velocity.x < 0 && velocity.y > 0)
			direction = Direction.UPLEFT;
		else if (velocity.x < 0 && velocity.y < 0)
			direction = Direction.LEFTDOWN;

		if (oldDirection != direction || currState != prevState)
			stateTimer = 0;

		b2body.setLinearVelocity(velocity);
		position.set(b2body.getPosition());
	}
}
