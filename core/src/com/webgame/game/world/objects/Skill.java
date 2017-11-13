package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	protected Integer numFrames;

	protected SpriteBatch batch;
	protected String spritePath;
	protected Texture spriteTexture;

	protected boolean isActive;
	protected boolean isAOE;
	protected boolean isFalling;
	protected boolean isTimed;
	protected boolean isStatic;

	protected final float absVelocity = 10f / PPM;

	protected Rectangle area;
	protected float skillTimer;

	public Skill(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
		setBatch(batch);
		setSpriteTexture(spriteTexture);
		initSkill(numFrames);
	}

	protected void initSkill(Integer numFrames) throws Exception {
		setNumFrames(numFrames);
		ArrayList<T> objs = new ArrayList<T>();
		for (int i = 0; i < numFrames; i++) {
			T obj = createObject();
			obj.initSkill(batch, spriteTexture);
			objs.add(obj);
		}
		setSkillObjects(objs);
	}

	public void cast(Vector2 playerPosition, Vector2 targetPosition) {
		setActive(true);
		clearTimers();

		skillVelocity = calculateVelocity(playerPosition, targetPosition);

		if (area != null)
			area.setPosition(targetPosition.x - area.getWidth() / 2, targetPosition.y - area.getHeight() / 2);

		for (int i = 0; i < numFrames; i++) {
			T obj = skillObjects.get(i);
			obj.setTargetPosition(targetPosition);
			obj.setPlayerPosition(playerPosition);
			obj.setArea(area);
			obj.setVelocity(skillVelocity);
			initFrame(obj);
		}
	}

	public void animateSkill(float dt) {
		if (skillObjects == null || !isActive)
			return;

		customAnimation(dt);

		for (int i = 0; i < numFrames; i++) {
			T frame = skillObjects.get(i);
			if (!frame.isActive())
				continue;
			updateFrame(frame, dt);
			frame.draw();
		}
		
		afterCustomAnimation();
	}

	protected abstract void customAnimation(float dt);
	protected abstract void afterCustomAnimation();
	protected abstract T createObject();
	protected abstract void initFrame(T frame);

	protected void updateFrame(T frame, float dt) {
		frame.update(dt);
		initFrame(frame);
	}

	public void drawShape(ShapeRenderer sr) {
		sr.setColor(Color.BLUE);
		for (int i = 0; i < numFrames; i++) {
			T obj = skillObjects.get(i);
			obj.drawShape(sr);
		}
		if (area != null)
			sr.rect(area.getX(), area.getY(), area.getWidth(), area.getHeight());
	}

	public float getRandomPos(float min, float max) {
		Double random = min + Math.random() * (max - min);
		return random.floatValue();
	}

	protected void clearTimers() {
		skillTimer = 0;
	}

	protected void updateTimers(float dt) {
		skillTimer += dt;
	}

	public boolean isAOE() {
		return isAOE;
	}

	public void setAOE(boolean isAOE) {
		this.isAOE = isAOE;
	}

	public boolean isFalling() {
		return isFalling;
	}

	public void setFalling(boolean isFalling) {
		this.isFalling = isFalling;
	}

	public boolean isTimed() {
		return isTimed;
	}

	public void setTimed(boolean isTimed) {
		this.isTimed = isTimed;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
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

	protected Vector2 calculateVelocity(Vector2 playerPosition, Vector2 targetPosition) {
		Vector2 vec = new Vector2(targetPosition.x - playerPosition.x, targetPosition.y - playerPosition.y);
		float len = vec.len();
		vec.x = absVelocity * vec.x / len;
		vec.y = absVelocity * vec.y / len;

		return vec;
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

	public Integer getNumFrames() {
		return numFrames;
	}

	public void setNumFrames(Integer numFrames) throws Exception {
		if (numFrames <= 0)
			throw new Exception("Skill objects num cannot be less or equal zero");

		this.numFrames = numFrames;
	}
}
