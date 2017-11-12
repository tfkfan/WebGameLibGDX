package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import static com.webgame.game.Configs.PPM;

import java.util.ArrayList;

public abstract class Skill<T extends SkillObject> {
	protected Double damage;
	protected String title;

	protected Vector2 skillVelocity;

	protected ArrayList<T> skillObjects;
	protected Integer skillObjectsNum;

	protected SpriteBatch batch;
	protected String spritePath;
	protected Texture spriteTexture;

	protected boolean isActive;

	protected final float absVelocity = 10f / PPM;

	protected Rectangle area;
	protected float skillTimer;

	public Skill() {

	}

	public Skill(SpriteBatch batch, Texture spriteTexture) {
		setBatch(batch);
		setSpriteTexture(spriteTexture);
	}

	public void cast(Vector2 playerPosition, Vector2 targetPosition) {
		setActive(true);
		updateTimers();
		skillVelocity = calculateVelocity(playerPosition, targetPosition);
		if (area != null)
			area.setPosition(targetPosition.x - area.getWidth() / 2, targetPosition.y - area.getHeight() / 2);

		for (int i = 0; i < skillObjectsNum; i++) {
			SkillObject obj = skillObjects.get(i);
			obj.setTargetPosition(targetPosition);
			obj.setPlayerPosition(playerPosition);
			obj.setArea(area);
			obj.initPositions();
			obj.setVelocity(skillVelocity);
		}

		afterCast();
	}

	public void afterCast() {

	}

	public void drawShape(ShapeRenderer sr) {
		sr.setColor(Color.BLUE);
		for (int i = 0; i < skillObjectsNum; i++) {
			SkillObject obj = skillObjects.get(i);
			obj.drawShape(sr);
		}
		if (area != null)
			sr.rect(area.getX(), area.getY(), area.getWidth(), area.getHeight());
	}

	public void updateTimers() {
		skillTimer = 0;
	}

	public void setSpriteTexture(Texture spriteTexture) {
		this.spriteTexture = spriteTexture;
	}

	public Texture getSpriteTexture() {
		return spriteTexture;
	}

	public Rectangle getArea() {
		return area;
	}

	public void setArea(Rectangle area) {
		this.area = area;
	}

	public Vector2 calculateVelocity(Vector2 playerPosition, Vector2 targetPosition) {
		Vector2 vec = new Vector2(targetPosition.x - playerPosition.x, targetPosition.y - playerPosition.y);
		float len = vec.len();
		vec.x = absVelocity * vec.x / len;
		vec.y = absVelocity * vec.y / len;

		return vec;
	}

	public void animateSkill(float dt) {
		if (skillObjects == null || !isActive)
			return;
		customAnimation(dt);
		afterAnimation();
	}

	public abstract void customAnimation(float dt);

	public void afterAnimation() {

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

	public ArrayList<T> getSkillObjects() {
		return skillObjects;
	}

	public void setSkillObjects(ArrayList<T> skillObjects) {
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

	public void createSkill(Integer objNum) {
		try {
			setSkillObjectsNum(objNum);

			ArrayList<T> objs = new ArrayList<T>();
			for (int i = 0; i < objNum; i++) {
				T obj = createObject();
				obj.initSkill(batch, spriteTexture);
				objs.add(obj);
			}

			setSkillObjects(objs);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public abstract T createObject();

}
