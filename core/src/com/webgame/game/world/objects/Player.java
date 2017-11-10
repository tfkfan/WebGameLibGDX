package com.webgame.game.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.webgame.game.state.Direction;
import com.webgame.game.state.PlayerState;
import static com.webgame.game.Configs.PPM;

public abstract class Player extends Sprite implements Movable, Animated {
	protected World world;
	protected Body b2body;

	protected Texture spriteTexture;
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

	protected float xOffset;
	protected float yOffset;
	
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
		xOffset = yOffset = 0;
		
		velocity = new Vector2();

		prevState = PlayerState.STAND;
		currState = PlayerState.STAND;

		direction = Direction.UP;
		oldDirection = Direction.UP;

		stateTimer = 0;
		
		this.setBounds(0, 0, 60/PPM, 60/PPM);
	}
	
	public void definePlayer(World world) {
		this.world = world;
		
		BodyDef bdef = new BodyDef();
		bdef.position.set(0, 0);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		
		shape.setRadius(20/PPM);
		
		fdef.shape = shape;
		b2body.createFixture(fdef);	
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
	public void move(float dt) {
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
		setPosition(b2body.getPosition().x - xOffset, b2body.getPosition().y - yOffset);
		
		updateStateTimer(dt);
		setRegion(getFrame());
	}
}
