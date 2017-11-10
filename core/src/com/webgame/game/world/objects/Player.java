package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import static com.webgame.game.Configs.PPM;

public abstract class Player extends GameObject {

	protected Double healthPoints;
	protected Double manaPoints;

	protected String playerName;
	protected Integer level;

	public Player(SpriteBatch batch, String spritePath) {
		super(batch, spritePath);

	}

	@Override
	public void createObject(World world) {
		setWorld(world);

		BodyDef bdef = new BodyDef();
		bdef.position.set(0, 0);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();

		shape.setRadius(20 / PPM);

		fdef.shape = shape;
		b2body.createFixture(fdef);
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

}
