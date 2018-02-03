package com.webgame.game.world.skills;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.world.player.Player;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

import static com.webgame.game.Configs.PPM;

import java.util.ArrayList;

public abstract class Skill<T extends SkillSprite> implements Cloneable {
    protected Double damage;
    protected String title;

    protected Vector2 skillVelocity;
    protected Vector2 targetPosition;

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
    protected boolean isMarked;
    protected boolean isBuff;
    protected boolean isHeal;

    protected final float absVelocity = 10f / PPM;
    protected float skillDuration = 100;

    protected Rectangle area;
    protected float skillTimer;

    public Skill(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
        setBatch(batch);
        setSpriteTexture(spriteTexture);
        initSkill(numFrames);
        setDamage(1d);
    }

    @SuppressWarnings("unchecked")
    public Skill<T> clone() {
        try {
            Skill<T> newSkill = (Skill<T>) super.clone();
            newSkill.initSkill(numFrames);
            if (this.getArea() != null)
                newSkill.setArea(new Rectangle(this.getArea()));
            newSkill.setDamage(damage);
            return newSkill;
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("null");
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

        //setDamage(1d);
        setSkillObjects(objs);
    }

    public void cast(Vector2 playerPosition, Vector2 targetPosition) {
        setActive(true);
        setMarked(false);
        clearTimers();

        setTargetPosition(targetPosition);

        for (int i = 0; i < numFrames; i++) {
            T obj = skillObjects.get(i);
            initFrame(obj, playerPosition, targetPosition);
        }
    }

    public void animateSkill(float dt) {
        if (skillObjects == null || !isActive)
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

        if (isTimed && skillTimer >= skillDuration || !isTimed && !flag)
            resetSkill();

        afterCustomAnimation();
    }

    protected void resetObject(SkillSprite obj) {
        obj.setActive(false);
        obj.setStatic(false);
        obj.setPosition(0, 0);
        obj.setAOE(isAOE);
        obj.setMarked(false);
        obj.setFinalAnimated(false);
    }

    protected void resetSkill() {
        setActive(false);
        setMarked(false);
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

    public void skillCollision(Player player) {
        if (player.getActorState().getHealthPoints() <= 0) {
            player.getActorState().setHealthPoints(player.getActorState().getMaxHealthPoints());
            return;
        }

        Rectangle pRect = player.getPlayerRectangle();
        boolean isAOE = isAOE();
        boolean isFalling = isFalling();
        boolean isTimed = isTimed();
        boolean isStatic = isStatic();

        if(isBuff() || isHeal()){
            if(Intersector.overlaps(player.getPlayerShape(), getArea())) {
                if (!isMarked)
                 player.getActorState().setHealthPoints(player.getActorState().getMaxHealthPoints());
                if (!isTimed)
                    isMarked = true;
            }
        }else if ((!isAOE || isAOE && isFalling) && !isStatic) {
            for (T frame : this.skillObjects) {
                if (!frame.isMarked() && (!isAOE || frame.isStatic()) && Intersector.overlaps(player.getPlayerShape(), frame.getBoundingRectangle())) {
                    player.getActorState().setHealthPoints(player.getActorState().getHealthPoints() - getDamage().intValue());
                    frame.setMarked(true);

                }
            }
        } else if (isAOE || !isAOE && isStatic) {
            if (isAOE && !isFalling && Intersector.overlaps(player.getPlayerShape(), getArea())
                    || !isAOE && isStatic && player.getPlayerShape().contains(getTargetPosition())) {
                if (!isMarked)
                    player.getActorState().setHealthPoints(player.getActorState().getHealthPoints() - getDamage().intValue());
                if (!isTimed)
                    isMarked = true;
            }
        }
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

    public boolean isAOE() {
        return isAOE;
    }

    public Skill<T> setAOE(boolean isAOE) {
        this.isAOE = isAOE;
        return this;
    }

    public boolean isBuff() {
        return isBuff;
    }

    public void setBuff(boolean buff) {
        isBuff = buff;
    }

    public boolean isHeal() {
        return isHeal;
    }

    public void setHeal(boolean heal) {
        isHeal = heal;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public Skill<T> setFalling(boolean isFalling) {
        this.isFalling = isFalling;
        return this;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public Skill<T> setMarked(boolean isMarked) {
        this.isMarked = isMarked;
        return this;
    }

    public boolean isTimed() {
        return isTimed;
    }

    public Skill<T> setTimed(boolean isTimed) {
        this.isTimed = isTimed;
        return this;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public Skill<T> setStatic(boolean isStatic) {
        this.isStatic = isStatic;
        return this;
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

    public Skill<T> setActive(boolean isActive) {
        this.isActive = isActive;
        return this;
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

    public Skill<T> setDamage(Double damage) {
        this.damage = damage;
        return this;
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
