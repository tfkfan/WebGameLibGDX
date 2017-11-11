package com.webgame.game.world.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.webgame.game.Configs.PPM;

public abstract class Player extends WorldGameObject {

	protected Double healthPoints;
	protected Double manaPoints;

	protected String playerName;
	protected Integer level;
	
	protected Skill<?> skill;

	public Player() {
		super();
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
	
	public void attack(float targetX, float targetY) {
		if(!skill.isActive())
			skill.cast(new Vector2(getX(), getY()), new Vector2(targetX, targetY), getDirection());
	}
	
	public Skill<?> getSkill() {
		return skill;
	}

	public void setSkill(Skill<?> skill) {
		this.skill = skill;
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
	
	@Override
	public void move(float dt){
		this.updateDirection();
		super.move(dt);
	}
	
	
	public void animateSkills(float dt){
		skill.animateSkill(dt);
	}

}
