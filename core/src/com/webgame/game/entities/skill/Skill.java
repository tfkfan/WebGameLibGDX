package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.entities.Entity;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.DirectionState;
import com.webgame.game.enums.EntityState;
import com.webgame.game.skill_animations.SkillAnimation;
import com.webgame.game.world.common.IUpdatable;

import java.util.List;

import static com.webgame.game.Configs.PPM;

public abstract class Skill extends Entity implements IUpdatable {
    protected static final float SKILL_WIDTH = 50 / PPM;
    protected static final float SKILL_HEIGHT = 50 / PPM;
    protected static final float SKILL_RADIUS = 30 / PPM;

    protected Long level;
    protected String title;

    protected float damage;
    protected float heal;
    protected float stateTimer;

    protected DirectionState directionState;

    protected Vector2 target;

    protected transient Player player;
    protected List<SkillAnimation> animations;

    public Skill(Player player) {
        super();
        init(player);
    }

    protected void init(Player player) {
        title = "Skill";

        damage = 0f;
        heal = 0f;

        clearTimers();

        entityState = EntityState.INACTIVE;

        setPlayer(player);
    }

    public void cast(Vector2 targetPosition) {
        setTarget(targetPosition);
        setEntityState(EntityState.ACTIVE);
        setPosition(new Vector2(player.getPosition()));
    }

    public abstract void updateAnimations(float dt);

    @Override
    public void update(float dt) {
        if (getEntityState().equals(EntityState.INACTIVE)) {
            clearTimers();
            return;
        }

        updateAnimations(dt);

        stateTimer += dt;

        if (stateTimer >= 50)
            clearTimers();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (animations == null || animations.isEmpty() || getEntityState().equals(EntityState.INACTIVE))
            return;
        for (SkillAnimation animation : animations)
            if (animation.getEntityState().equals(EntityState.ACTIVE))
                animation.draw(batch, parentAlpha);
    }

    public List<SkillAnimation> getAnimations() {
        return animations;
    }

    public void setAnimations(List<SkillAnimation> animations) {
        this.animations = animations;
    }

    public void clearTimers() {
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

    public DirectionState getDirectionState() {
        return directionState;
    }

    public void setDirectionState(DirectionState directionState) {
        this.directionState = directionState;
    }
}
