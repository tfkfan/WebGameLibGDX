package com.webgame.game.world.common;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GameActor extends Actor implements IUpdatable, IFramed {
	protected Vector2 velocity;
	protected float xOffset;
	protected float yOffset;
	protected GameActorState actorState;
	protected static final int dirs = 8;

	public GameActor(){
		init();
	}

	public GameActorState getActorState() {
		return actorState;
	}

	protected void init(){
		actorState = new GameActorState();
		velocity = new Vector2(0,0);
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

	@Override
	public void draw(Batch batch, float parentAlpha){
		batch.draw(getFrame(), getX() - getXOffset(), getY() - getYOffset(), getWidth(), getHeight());
	}

	public class GameActorState{
		protected Integer healthPoints;
		protected Integer manaPoints;
		protected Integer maxHealthPoints;
		protected Integer maxManaPoints;
		protected String name;
		protected Integer level;

		public GameActorState(){

		}

		public Integer getHealthPoints() {
			return healthPoints;
		}

		public void setHealthPoints(Integer healthPoints) {
			this.healthPoints = healthPoints;
		}

		public Integer getManaPoints() {
			return manaPoints;
		}

		public void setManaPoints(Integer manaPoints) {
			this.manaPoints = manaPoints;
		}

		public Integer getMaxHealthPoints() {
			return maxHealthPoints;
		}

		public void setMaxHealthPoints(Integer maxHealthPoints) {
			this.maxHealthPoints = maxHealthPoints;
		}

		public Integer getMaxManaPoints() {
			return maxManaPoints;
		}

		public void setMaxManaPoints(Integer maxManaPoints) {
			this.maxManaPoints = maxManaPoints;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getLevel() {
			return level;
		}

		public void setLevel(Integer level) {
			this.level = level;
		}

	}
}
