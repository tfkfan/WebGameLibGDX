package com.webgame.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.state.PlayerState;

public abstract class Player implements GameObject {
	protected Texture spriteTexture;
	protected Sprite sprite;
	protected Vector2 position;
	protected Vector2 velosity;
	protected float stateTimer;

	protected PlayerState currState;
	protected PlayerState prevState;

	protected Double healthPoints;
	protected Double manaPoints;

	protected String playerName;
	protected Integer level;

	protected Animation<TextureRegion> walkAnimation;

	public Player(String spritePath) {
		spriteTexture = new Texture(Gdx.files.internal(spritePath));

		prevState = PlayerState.STAND;
		currState = PlayerState.STAND;

		stateTimer = 0;

		sprite = new Sprite(spriteTexture, 20, 20, 50, 50);
		sprite.setPosition(10, 10);
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

	public Animation<TextureRegion> getWalkAnimation() {
		return walkAnimation;
	}

	public void setWalkAnimation(Animation<TextureRegion> walkAnimation) {
		this.walkAnimation = walkAnimation;
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

	public Vector2 getVelosity() {
		return velosity;
	}

	public void setVelosity(Vector2 velosity) {
		this.velosity = velosity;
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

	
	public abstract void draw(SpriteBatch batch, float dt);
		
}
