package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Skill implements Movable {
	protected Double damage;
	protected String title;

	protected Vector2 skillVelocity;

	protected SkillObject[] skillObjects;
	protected Integer skillObjectsNum;

	protected SpriteBatch batch;
	protected String spritePath;
	
	protected boolean isActive;

	@Override
	public void move(float dt) {
		if (skillObjects == null)
			return;

		for (int i = 0; i < skillObjectsNum; i++) {
			SkillObject obj = skillObjects[i];

			if (!obj.isActive())
				continue;

			obj.setVelocity(skillVelocity);
			obj.move(dt);
		}

		handleCollision();
	}

	public void cast(Vector2 vec) {
		setActive(true);
		for (int i = 0; i < skillObjectsNum; i++) {
			skillObjects[i].setSkillActive(true);
			skillObjects[i].setPosition(vec.x, vec.y);
		}
	}

	public void handleCollision() {
		for (int i = 0; i < skillObjectsNum; i++) {
			handleCollision(skillObjects[i]);
			setActive(skillObjects[i].isActive());
		}
	}

	public void handleCollision(SkillObject obj) {
		float x = obj.getX();
		float y = obj.getY();
		if (x < -10 || y < -10 || x > 200 || y > 200)
			obj.setSkillActive(false);
	}

	public void drawSkills() {
		if (skillObjects == null)
			return;
		for (int i = 0; i < skillObjectsNum; i++) {
			if (skillObjects[i].isActive())
				skillObjects[i].draw(batch);
		}
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Skill(SpriteBatch batch, String spritePath) {
		setBatch(batch);
		setSpritePath(spritePath);
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

	public abstract void initSkill();
}
