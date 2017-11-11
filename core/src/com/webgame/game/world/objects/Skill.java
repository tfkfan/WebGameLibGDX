package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.state.Direction;
import static com.webgame.game.Configs.PPM;

public abstract class Skill<T extends SkillObject> {
	protected Double damage;
	protected String title;

	protected Vector2 skillVelocity;

	protected SkillObject[] skillObjects;
	protected Integer skillObjectsNum;

	protected SpriteBatch batch;
	protected String spritePath;

	protected boolean isActive;

	protected final float absVelocity = 10f / PPM;

	protected Rectangle area;

	public Skill() {

	}

	public Skill(SpriteBatch batch, String spritePath) {
		setBatch(batch);
		setSpritePath(spritePath);
	}

	public void cast(Vector2 playerPosition, Vector2 targetPosition, Direction direction) {
		setActive(true);
		skillVelocity = getVelocityByDirection(direction);

		for (int i = 0; i < skillObjectsNum; i++) {
			SkillObject obj = skillObjects[i];
			obj.setTargetPosition(targetPosition);
			obj.setPlayerPosition(playerPosition);
			obj.initPositions();
			obj.setVelocity(skillVelocity);
		}
	}

	public Rectangle getArea() {
		return area;
	}

	public void setArea(Rectangle area) {
		this.area = area;
	}

	public Vector2 getVelocityByDirection(Direction direction) {
		Vector2 vec = new Vector2(0, 0);
		switch (direction) {
		case UP:
			vec.y = absVelocity;
			break;
		case UPRIGHT:
			vec.y = absVelocity;
			vec.x = absVelocity;
			break;
		case RIGHT:
			vec.x = absVelocity;
			break;
		case RIGHTDOWN:
			vec.y = -absVelocity;
			vec.x = absVelocity;
			break;
		case DOWN:
			vec.y = -absVelocity;
			break;
		case LEFTDOWN:
			vec.y = -absVelocity;
			vec.x = -absVelocity;
			break;
		case LEFT:
			vec.x = -absVelocity;
			break;
		case UPLEFT:
			vec.y = absVelocity;
			vec.x = -absVelocity;
			break;
		default:
			break;
		}

		return vec;
	}

	public void animateSkill(float dt) {
		if (skillObjects == null || !isActive)
			return;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public Double getDamage() {
		return damage;
	}

	public void setDamage(Double damage) {
		this.damage = damage;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Vector2 getSkillVelocity() {
		return skillVelocity;
	}

	public void setSkillVelocity(Vector2 skillVelocity) {
		this.skillVelocity = skillVelocity;
	}

	public String getSpritePath() {
		return spritePath;
	}

	public void setSpritePath(String spritePath) {
		this.spritePath = spritePath;
	}

	public SkillObject[] getSkillObjects() {
		return skillObjects;
	}

	public void setSkillObjects(SkillObject[] skillObjects) {
		this.skillObjects = skillObjects;
	}

	public Integer getSkillObjectsNum() {
		return skillObjectsNum;
	}

	public void setSkillObjectsNum(Integer skillObjectsNum) throws Exception {
		if (skillObjectsNum <= 0)
			throw new Exception("Skill objects num cannot be less or equal zero");

		this.skillObjectsNum = skillObjectsNum;
	}

	public void initSkill(Integer objNum) {

		try {
			setSkillObjectsNum(objNum);

			SkillObject[] objs = createObjectsArray(objNum);
			for (int i = 0; i < objNum; i++) {
				objs[i] = createObject();
				objs[i].initSkill(batch, spritePath);
			}

			setSkillObjects(objs);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public abstract T createObject();

	public abstract T[] createObjectsArray(Integer num);
}
