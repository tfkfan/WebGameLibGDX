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

	public Skill(SpriteBatch batch, String spritePath) {
		setBatch(batch);
		setSpritePath(spritePath);
	}

	@Override
	public void move(float dt) {
		if (skillObjects == null)
			return;

		for (int i = 0; i < skillObjectsNum; i++) {
			SkillObject obj = skillObjects[i];
			
			if(!obj.getIsActive())
				continue;
			
			obj.setVelocity(skillVelocity);
			obj.move(dt);
		}
	}

	public void drawSkills() {
		if (skillObjects == null)
			return;
		for (int i = 0; i < skillObjectsNum; i++){
			if(skillObjects[i].getIsActive())
				skillObjects[i].draw(batch);
		}
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
