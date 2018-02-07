package com.webgame.game.entities.skill;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.webgame.game.entities.Entity;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.DirectionState;
import com.webgame.game.enums.SkillState;
import com.webgame.game.world.common.IUpdatable;
import com.webgame.game.world.skills.collision.SkillCollision;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

import java.util.ArrayList;

import static com.webgame.game.Configs.PPM;

public abstract class Skill extends Entity implements IUpdatable{
    protected static final float SKILL_WIDTH = 50/PPM;
    protected static final float SKILL_HEIGHT = 50/PPM;

    protected Long level;
    protected String title;

    protected float damage;
    protected float heal;
    protected float stateTimer;

    protected Rectangle skillZone;
    protected SkillState skillState;
    protected DirectionState directionState;

    protected Vector2 target;

    protected transient Player player;
    protected transient Animation<TextureRegion> animation;
    protected transient TextureRegion standRegion;

    public Skill(Player player)  {
        super();
        init(player);
    }

    protected void init(Player player) {
        title = "Skill";

        damage = 0f;
        heal = 0f;
        stateTimer = 0f;

        skillZone = new Rectangle(getPosition().x, getPosition().y, SKILL_WIDTH, SKILL_HEIGHT);

        skillState = SkillState.INACTIVE;

        setPlayer(player);
    }

    public void cast(Vector2 targetPosition) {
        setTarget(targetPosition);
        setSkillState(SkillState.ACTIVE);
        setPosition(new Vector2(player.getPosition()));
        getSkillZone().setPosition(new Vector2(player.getPosition()));
    }

    @Override
    public void update(float dt){
        if(skillState.equals(SkillState.ACTIVE)){
            Vector2 v = new Vector2(target.x -getSkillZone().width/2 - player.getPosition().x, target.y -getSkillZone().height/2- player.getPosition().y);
            v = v.nor();
            float absVel = 10/PPM;
            v.scl(absVel);

            getPosition().set(new Vector2(getPosition()).add(v));
            getSkillZone().setPosition(new Vector2(getPosition()));

            if(getSkillZone().contains(target)){
                setSkillState(SkillState.INACTIVE);
                clearTimers();
            }
        }
        stateTimer += dt;

        if (stateTimer >= 10)
            clearTimers();
    }

    @Override
    public void draw(Batch batch, float parentAlpha){

    }

    public void clearTimers(){
        stateTimer = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getHeal() {
        return heal;
    }

    public void setHeal(float heal) {
        this.heal = heal;
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public void setStateTimer(float stateTimer) {
        this.stateTimer = stateTimer;
    }

    public Rectangle getSkillZone() {
        return skillZone;
    }

    public void setSkillZone(Rectangle skillZone) {
        this.skillZone = skillZone;
    }

    public SkillState getSkillState() {
        return skillState;
    }

    public void setSkillState(SkillState skillState) {
        this.skillState = skillState;
    }

    public DirectionState getDirectionState() {
        return directionState;
    }

    public void setDirectionState(DirectionState directionState) {
        this.directionState = directionState;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public TextureRegion getStandRegion() {
        return standRegion;
    }

    public void setStandRegion(TextureRegion standRegion) {
        this.standRegion = standRegion;
    }
}
