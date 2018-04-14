package com.webgame.game.entities.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.webgame.game.Configs;
import com.webgame.game.entities.WorldEntity;
import com.webgame.game.entities.attributes.PlayerAttributes;
import com.webgame.game.entities.player.impl.Mage;
import com.webgame.game.entities.skill.Skill;
import com.webgame.game.enums.*;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import com.webgame.game.world.common.IDTOUpdatable;
import com.webgame.game.world.common.IUpdatable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.webgame.game.Configs.PPM;

public abstract class Player extends WorldEntity implements IUpdatable, IDTOUpdatable<PlayerDTO> {
    protected float stateTimer;
    protected float attackTimer;

    protected DirectionState directionState;
    protected DirectionState oldDirectionState;
    protected PlayerState playerState;
    protected PlayerMoveState currAnimationState;
    protected PlayerAttackState currentAttackState;
    protected PlayerMoveState prevAnimationState;

    protected Array<Animation<TextureRegion>> animations;
    protected Array<Animation<TextureRegion>> attackAnimations;
    protected TextureRegion[] standRegions;

    protected Integer healthPoints;
    protected Integer manaPoints;
    protected Integer maxHealthPoints;
    protected Integer maxManaPoints;
    protected String name;
    protected Integer level;

    protected Map<String, Skill> activeSkills;
    protected List<Skill> allSkills;

    protected int currentSkill;

    public Player() {
        super();
    }

    @Override
    public void init() {
        super.init();

        directionState = DirectionState.UP;
        oldDirectionState = directionState;

        playerState = PlayerState.ALIVE;

        currentAttackState = PlayerAttackState.SAFE;

        currAnimationState = PlayerMoveState.STAND;
        prevAnimationState = currAnimationState;

        setHealthPoints(1000);
        setMaxHealthPoints(1000);

        activeSkills = new ConcurrentHashMap<>();

        currentSkill = 0;

        clearTimers();
        setBounds(0, 0, 60 / PPM, 60 / PPM);
    }

    public static Player createPlayer(World world) {
        Player player = new Mage(Configs.PLAYERSHEETS_FOLDER + "/mage.png");
        player.createObject(world);
        return player;
    }

    public Skill castSkill(Vector2 target, String id) {
        Skill currSkill = getCurrentSkill();
        if (currSkill == null)
            return null;

        // Long end = getCurrentSkill().getStart() + getCurrentSkill().getCooldown();
        //Long currentTime = System.currentTimeMillis();

        //if (currentTime < end)
        //   return null;

        //  currSkill.setStart(System.currentTimeMillis());

        final Skill skill = currSkill.createCopy();
        skill.cast(target);
        activeSkills.put(id, skill);
        return skill;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (getB2body() != null)
            getB2body().setTransform(x, y, 0);
    }

    public boolean isAttackFinished() {
        Integer index = directionState.getDirIndex();
        return attackAnimations.get(index).isAnimationFinished(attackTimer);
    }

    public void clearTimers() {
        stateTimer = 0;
        attackTimer = 0;
    }

    @Override
    public TextureRegion getFrame() {
        TextureRegion region;

        PlayerMoveState currState = getCurrAnimationState();
        PlayerAttackState attackState = getCurrentAttackState();

        Integer index = directionState.getDirIndex();

        if (attackState.equals(PlayerAttackState.BATTLE))
            region = attackAnimations.get(index).getKeyFrame(attackTimer, false);
        else {
            switch (currState) {
                case WALK:
                    region = animations.get(index).getKeyFrame(stateTimer, true);
                    break;
                case STAND:
                default:
                    region = standRegions[this.directionState.getDirIndex()];
                    break;
            }
        }

        return region;
    }

    @Override
    public void updateBy(PlayerDTO dto) {
        setPosition(dto.getPosition());
        setVelocity(dto.getVelocity());
        setName(dto.getName());
        setId(dto.getId());
        setCurrAnimationState(dto.getPlayerMoveState());
        setOldDirectionState(getDirectionState());
        setDirectionState(dto.getDirectionState());
        setCurrentAttackState(dto.getPlayerAttackState());
        setCurrentSkillIndex(dto.getCurrentSkillIndex());
    }

    @Override
    public void update(float dt) {
        if (getVelocity().isZero())
            setCurrAnimationState(PlayerMoveState.STAND);
        else
            setCurrAnimationState(PlayerMoveState.WALK);

        if (getCurrentAttackState().equals(PlayerAttackState.BATTLE)) {
            if (isAttackFinished()) {
                setCurrentAttackState(PlayerAttackState.SAFE);
                attackTimer = 0;
            } else
                attackTimer += dt;
        }

        stateTimer += dt;

        if (stateTimer >= 10)
            clearTimers();
    }

    public Integer getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(Integer healthPoints) {
        this.healthPoints = healthPoints;
    }

    public Integer getManaPoints() {
        return manaPoints;
    }

    public void setManaPoints(Integer manaPoints) {
        this.manaPoints = manaPoints;
    }

    public Integer getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public void setMaxHealthPoints(Integer maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    public Integer getMaxManaPoints() {
        return maxManaPoints;
    }

    public void setMaxManaPoints(Integer maxManaPoints) {
        this.maxManaPoints = maxManaPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public int getCurrentSkillIndex() {
        return currentSkill;
    }

    public void setCurrentSkillIndex(int index) {
        this.currentSkill = index;
    }

    public Map<String, Skill> getActiveSkills() {
        return activeSkills;
    }

    public List<Skill> getAllSkills() {
        return allSkills;
    }

    public void setAllSkills(List<Skill> allSkills) {
        this.allSkills = allSkills;
    }

    public Skill getCurrentSkill() {
        if (currentSkill >= 0 && currentSkill < allSkills.size())
            return allSkills.get(currentSkill);
        return null;
    }

    public void setCurrentSkill(int currentSkill) {
        this.currentSkill = currentSkill;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public DirectionState getDirectionState() {
        return directionState;
    }

    public void setDirectionState(DirectionState directionState) {
        this.directionState = directionState;
    }

    public DirectionState getOldDirectionState() {
        return oldDirectionState;
    }

    public void setOldDirectionState(DirectionState oldDirectionState) {
        this.oldDirectionState = oldDirectionState;
    }

    public Array<Animation<TextureRegion>> getAnimations() {
        return animations;
    }

    public void setAnimations(Array<Animation<TextureRegion>> animations) {
        this.animations = animations;
    }

    public Array<Animation<TextureRegion>> getAttackAnimations() {
        return attackAnimations;
    }

    public void setAttackAnimations(Array<Animation<TextureRegion>> attackAnimations) {
        this.attackAnimations = attackAnimations;
    }

    public void setCurrAnimationState(PlayerMoveState state) {
        this.currAnimationState = state;
    }

    public PlayerMoveState getCurrAnimationState() {
        return currAnimationState;
    }

    public PlayerMoveState getPrevAnimationState() {
        return prevAnimationState;
    }

    public void setPrevAnimationState(PlayerMoveState prevAnimationState) {
        this.prevAnimationState = prevAnimationState;
    }

    public PlayerAttackState getCurrentAttackState() {
        return currentAttackState;
    }

    public void setCurrentAttackState(PlayerAttackState currentAttackState) {
        this.currentAttackState = currentAttackState;
    }

    public TextureRegion[] getStandRegions() {
        return standRegions;
    }

    public void setStandRegions(TextureRegion[] standRegions) {
        this.standRegions = standRegions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Player))
            return false;
        if (((Player) obj).getId() != null)
            return ((Player) obj).getId().equals(getId());
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + hashCode();

        if (getId() != null)
            result = 31 * result + getId().hashCode();

        return result;
    }
}
