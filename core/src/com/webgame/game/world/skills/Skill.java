package com.webgame.game.world.skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.world.player.Player;
import com.webgame.game.world.skills.collision.SkillCollision;
import com.webgame.game.world.skills.skillsprites.SkillSprite;
import com.webgame.game.world.skills.state.SkillState;

import static com.webgame.game.Configs.PPM;

import java.util.ArrayList;

public abstract class Skill<T extends SkillSprite> implements Cloneable {
    protected Vector2 skillVelocity;
    protected Vector2 targetPosition;

    protected ArrayList<T> skillObjects;
    protected Integer numFrames;

    protected SpriteBatch batch;
    protected String spritePath;
    protected Texture spriteTexture;

    protected SkillState skillState;

    protected final float absVelocity = 10f / PPM;
    protected float skillDuration = 100;
    protected float skillTimer;

    public Skill(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
        skillState = new SkillState();
        setBatch(batch);
        setSpriteTexture(spriteTexture);
        initSkill(numFrames);
    }

    @SuppressWarnings("unchecked")
    public Skill<T> clone() {
        try {
            Skill<T> newSkill = (Skill<T>) super.clone();
            newSkill.skillState = new SkillState(this.getSkillState());
            newSkill.initSkill(numFrames);
            if (this.getSkillState().getArea() != null)
                newSkill.getSkillState().setArea(new Rectangle(this.getSkillState().getArea()));
            newSkill.getSkillState().setDamage(getSkillState().getDamage());
            return newSkill;
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    protected void initSkill(Integer numFrames) throws Exception {
        setNumFrames(numFrames);
        ArrayList<T> objs = new ArrayList<T>();
        for (int i = 0; i < numFrames; i++) {
            T obj = createObject();
            obj.initSkillSprite(batch, spriteTexture);
            objs.add(obj);
        }
        setSkillObjects(objs);
    }

    public void cast(Vector2 playerPosition, Vector2 targetPosition) {
        getSkillState().setActive(true);
        getSkillState().setMarked(false);
        clearTimers();
        setTargetPosition(targetPosition);
        for (int i = 0; i < numFrames; i++) {
            T obj = skillObjects.get(i);
            initFrame(obj, playerPosition, targetPosition);
        }
    }

    public void animateSkill(float dt) {
        if (skillObjects == null || !getSkillState().isActive())
            return;

        updateTimers(dt);
        customAnimation(dt);

        boolean flag = false;
        for (int i = 0; i < numFrames; i++) {
            T frame = skillObjects.get(i);
            if (!frame.isActive())
                continue;
            else
                flag = true;

            updateFrame(frame, dt);
            frame.draw();
        }

        if (getSkillState().isTimed() && skillTimer >= skillDuration || !getSkillState().isTimed() && !flag)
            resetSkill();

        afterCustomAnimation();
    }

    protected void resetObject(SkillSprite obj) {
        obj.setActive(false);
        obj.setStatic(false);
        obj.setPosition(0, 0);
        obj.setAOE(getSkillState().isAOE());
        obj.setMarked(false);
        obj.setFinalAnimated(false);
    }

    protected void resetSkill() {
        getSkillState().setActive(false);
        getSkillState().setMarked(false);
        for (int i = 0; i < numFrames; i++) {
            T obj = skillObjects.get(i);
            resetObject(obj);
        }
        clearTimers();
    }

    public void collisionFrame(T frame) {
        float x = frame.getX();
        float y = frame.getY();

        if (x < -10 || y < -10 || x > 10 || y > 10)
            frame.setActive(false);
    }

    public SkillState getSkillState() {
        return skillState;
    }

    protected void customAnimation(float dt) {
    }

    protected void afterCustomAnimation() {
    }

    protected abstract T createObject();

    protected abstract void initFrame(T frame, Vector2 playerPosition, Vector2 targetPosition);

    protected void updateFrame(T frame, float dt) {
        frame.update(dt);
        collisionFrame(frame);
    }


    public void drawShape(ShapeRenderer sr) {
        sr.setColor(Color.BLUE);
        for (int i = 0; i < numFrames; i++) {
            T obj = skillObjects.get(i);
            obj.drawShape(sr);
        }
        if (getSkillState().getArea() != null)
            sr.rect(getSkillState().getArea().getX(),getSkillState().getArea().getY(), getSkillState().getArea().getWidth(), getSkillState().getArea().getHeight());
    }

    public float getRandomPos(float min, float max) {
        Double random = min + Math.random() * (max - min);
        return random.floatValue();
    }

    public Vector2 getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Vector2 targetPosition) {
        this.targetPosition = targetPosition;
    }

    protected void clearTimers() {
        skillTimer = 0;
    }

    protected void updateTimers(float dt) {
        skillTimer += dt;
    }

    public void setSpriteTexture(Texture spriteTexture) {
        this.spriteTexture = spriteTexture;
    }

    public Texture getSpriteTexture() {
        return spriteTexture;
    }

    protected Vector2 calculateVelocity(Vector2 playerPosition, Vector2 targetPosition) {
        Vector2 vec = new Vector2(targetPosition.x - playerPosition.x, targetPosition.y - playerPosition.y);
        float len = vec.len();
        vec.x = absVelocity * vec.x / len;
        vec.y = absVelocity * vec.y / len;

        return vec;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
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
