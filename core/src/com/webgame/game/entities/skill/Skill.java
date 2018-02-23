package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.Configs;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.AnimatedEntity;
import com.webgame.game.entities.Entity;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.*;
import com.webgame.game.world.common.IUpdatable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static com.webgame.game.Configs.PPM;

public abstract class Skill extends Entity implements IUpdatable, Cloneable {
    protected static final float SKILL_WIDTH = 50 / PPM;
    protected static final float SKILL_HEIGHT = 50 / PPM;
    protected static final float SKILL_RADIUS = 30 / PPM;

    protected int animationsNum;

    protected Long level;
    protected String title;

    protected float damage;
    protected float heal;
    protected float stateTimer;

    protected DirectionState directionState;

    protected MoveState moveState;
    protected Vector2 target;

    protected transient Player player;
    protected List<SkillSprite> animations;

    public Skill(Player player) {
        super();
        init(player);
    }

    public Skill(Player player, TextureRegion standTexture, GameAnimation gameAnimation) {
        super();
        init(player);
        initAnimations(standTexture, gameAnimation, 1, SkillAnimationState.FULL_ANIMATION, false);
    }

    public Skill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, Boolean looping) {
        super();
        init(player);
        initAnimations(standTexture, gameAnimation, 1, SkillAnimationState.FULL_ANIMATION, looping);
    }

    public Skill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState) {
        super();
        init(player);
        initAnimations(standTexture, gameAnimation, 1, animationState, false);
    }

    public Skill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState, Boolean looping) {
        super();
        init(player);
        initAnimations(standTexture, gameAnimation, 1, animationState, looping);
    }

    public Skill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, Integer numFrames, SkillAnimationState animationState, Boolean looping) {
        super();
        init(player);
        initAnimations(standTexture, gameAnimation, numFrames, animationState, looping);
    }

    public Skill(Skill skill) {
        super();
        init(skill.getPlayer());
        copyAnimations(skill.getAnimations());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Class<?> clazz = this.getClass();

        try {
            return clazz.getDeclaredConstructor(Skill.class).newInstance(this);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;

    }

    protected void init(Player player) {
        setTitle("Skill");
        setDamage(0f);
        setHeal(0f);
        clearTimers();
        setEntityState(EntityState.INACTIVE);
        setMoveState(MoveState.MOVING);
        setPlayer(player);
    }

    public void cast(Vector2 targetPosition) {
        resetSkill();
        setTarget(targetPosition);
        setEntityState(EntityState.ACTIVE);
        setPosition(new Vector2(player.getPosition()));
    }

    public abstract void updateAnimations(float dt);

    public void initAnimations(TextureRegion standTexture, GameAnimation gameAnimation, Integer numFrames, SkillAnimationState animationState, Boolean looping) {
        List<SkillSprite> animations = new ArrayList<SkillSprite>();
        this.animationsNum = numFrames;
        for (int i = 0; i < numFrames; i++) {
            SkillSprite animation = new SkillSprite(standTexture, gameAnimation, animationState, looping);
            initAnimation(animation);
            animations.add(animation);
        }
        setAnimations(animations);
    }

    public void copyAnimations(List<SkillSprite> animations) {
        if (animations == null || animations.isEmpty())
            return;
        List<SkillSprite> newAnimations = new ArrayList<SkillSprite>();
        for (SkillSprite animation : animations) {
            newAnimations.add(new SkillSprite(animation));
        }
        setAnimations(newAnimations);
    }

    public void initAnimation(SkillSprite animation) {
    }

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
        for (SkillSprite animation : animations)
            if (animation.getEntityState().equals(EntityState.ACTIVE))
                animation.draw(batch, parentAlpha);
    }

    public void resetSkill() {
        setEntityState(EntityState.INACTIVE);
        setMoveState(MoveState.MOVING);
        clearTimers();
        for (SkillSprite animation : animations) {
            animation.clearTimers();
            animation.init();
            animation.setEntityState(EntityState.INACTIVE);
        }
    }

    public List<SkillSprite> getAnimations() {
        return animations;
    }

    public void setAnimations(List<SkillSprite> animations) {
        this.animations = animations;
    }

    public void clearTimers() {
        stateTimer = 0;
    }

    public MoveState getMoveState() {
        return moveState;
    }

    public void setMoveState(MoveState moveState) {
        this.moveState = moveState;
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

    protected class SkillSprite extends AnimatedEntity {
        protected float stateTimer;

        protected TextureRegion standTexture;
        protected GameAnimation animation;
        protected SkillAnimationState animationState;
        protected MoveState moveState;
        protected boolean looping;

        private Vector2 distance;

        public SkillSprite(SkillSprite animation) {
            init();
            setStandTexture(animation.getStandTexture());
            setAnimation(animation.getAnimation());
            setAnimationState(animation.getAnimationState());
            setMoveState(animation.getMoveState());
            setLooping(animation.isLooping());
            setSize(animation.getWidth(), animation.getHeight());
        }

        public SkillSprite() {
            init();
        }

        public SkillSprite(TextureRegion standTexture, GameAnimation animation) {
            setStandTexture(standTexture);
            setAnimation(animation);
            init();
        }

        public SkillSprite(TextureRegion standTexture, GameAnimation animation, SkillAnimationState animationState) {
            setStandTexture(standTexture);
            setAnimation(animation);
            setAnimationState(animationState);
            init();
        }

        public SkillSprite(TextureRegion standTexture, GameAnimation animation, SkillAnimationState animationState, Boolean looping) {
            setStandTexture(standTexture);
            setAnimation(animation);
            setAnimationState(animationState);
            setLooping(looping);
            init();
        }

        public void init() {
            setAnimationState(SkillAnimationState.FULL_ANIMATION);
            setEntityState(EntityState.INACTIVE);
            setMoveState(MoveState.MOVING);
            setLooping(false);
            stateTimer = 0;
            distance = new Vector2(0, 0);
            setSize(FrameSizes.LITTLE_SPHERE.getW(), FrameSizes.LITTLE_SPHERE.getH());
        }

        @Override
        public TextureRegion getFrame() {
            TextureRegion region = null;
            if ((SkillAnimationState.ANIMATION_ONLY.equals(getAnimationState()) ||
                    (SkillAnimationState.FULL_ANIMATION.equals(getAnimationState()))) && getMoveState().equals(MoveState.STATIC)) {
                region = animation.getAnimation().getKeyFrame(stateTimer, isLooping());
                setSize(FrameSizes.ANIMATION.getW(), FrameSizes.ANIMATION.getH());
            } else if (SkillAnimationState.STAND_TEXTURE.equals(getAnimationState()) ||
                    (SkillAnimationState.FULL_ANIMATION.equals(getAnimationState()))) {
                region = standTexture;
                setSize(FrameSizes.LITTLE_SPHERE.getW(), FrameSizes.LITTLE_SPHERE.getH());
            }

            return region;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            TextureRegion frame = getFrame();
            if (getEntityState().equals(EntityState.ACTIVE))
                batch.draw(frame, position.x - getXOffset() - getWidth() / 2, position.y - getYOffset() - getHeight() / 2, getWidth(), getHeight());
        }

        public void update(float dt) {
            if (getMoveState().equals(MoveState.MOVING)) {
                setPosition(getPosition().x + getVelocity().x - xOffset, getPosition().y + getVelocity().y - yOffset);
                distance.x += Math.abs(getVelocity().x);
                distance.y += Math.abs(getVelocity().y);
            }
        }

        public void clearTimers() {
            stateTimer = 0;
        }

        public boolean isAnimationFinished() {
            return animation.getAnimation().isAnimationFinished(stateTimer);
        }

        public float getStateTimer() {
            return stateTimer;
        }

        public void setStateTimer(float stateTimer) {
            this.stateTimer = stateTimer;
        }

        public Vector2 getDistance() {
            return distance;
        }

        public boolean isLooping() {
            return looping;
        }

        public void setLooping(boolean looping) {
            this.looping = looping;
        }

        public SkillAnimationState getAnimationState() {
            return animationState;
        }

        public void setAnimationState(SkillAnimationState animationState) {
            this.animationState = animationState;
        }

        public TextureRegion getStandTexture() {
            return standTexture;
        }

        public void setStandTexture(TextureRegion standTexture) {
            this.standTexture = standTexture;
        }

        public GameAnimation getAnimation() {
            return animation;
        }

        public void setAnimation(GameAnimation animation) {
            this.animation = animation;
        }

        public MoveState getMoveState() {
            return moveState;
        }

        public void setMoveState(MoveState moveState) {
            this.moveState = moveState;
        }


    }

}
