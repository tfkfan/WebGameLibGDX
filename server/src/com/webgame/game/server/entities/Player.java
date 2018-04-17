package com.webgame.game.server.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.entities.player.ClientPlayer;
import com.webgame.game.enums.DirectionState;
import com.webgame.game.enums.PlayerAttackState;
import com.webgame.game.enums.PlayerMoveState;
import com.webgame.game.enums.PlayerState;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.webgame.game.Configs.PPM;

public abstract class Player extends AnimatedEntity {
    protected PlayerMoveState playerMoveState;
    protected PlayerAttackState playerAttackState;
    protected DirectionState directionState;
    protected DirectionState oldDirectionState;
    protected PlayerState playerState;

    protected PlayerMoveState currAnimationState;
    protected PlayerAttackState currentAttackState;
    protected PlayerMoveState prevAnimationState;

    protected int currentSkillIndex;

    protected Integer healthPoints;
    protected Integer manaPoints;
    protected Integer maxHealthPoints;
    protected Integer maxManaPoints;

    protected List<Skill> allSkills;
    protected Map<String, Skill> skills;

    protected float radius = 20 / PPM;
    protected Circle shape;

    public Player() {

    }

    public Player(String id, String name, Vector2 position) {
        setId(id);
        setName(name);
        setPosition(position);
    }

    public void init(){
        directionState = DirectionState.UP;
        oldDirectionState = directionState;

        playerState = PlayerState.ALIVE;

        currentAttackState = PlayerAttackState.SAFE;

        currAnimationState = PlayerMoveState.STAND;
        prevAnimationState = currAnimationState;

        setHealthPoints(1000);
        setMaxHealthPoints(1000);

        setCurrentSkillIndex(0);

        setBounds(0, 0, 60 / PPM, 60 / PPM);
    }

    public Player(ClientPlayer clientPlayer) {
        updateBy(clientPlayer);
    }

    public void updateBy(Player playerDTO) {
        setPosition(playerDTO.getPosition());
        setVelocity(playerDTO.getVelocity());
        setPlayerAttackState(playerDTO.getPlayerAttackState());
        setPlayerMoveState(playerDTO.getPlayerMoveState());
        setDirectionState(playerDTO.getDirectionState());
        setCurrentSkillIndex(playerDTO.getCurrentSkillIndex());
    }

    public void updateBy(ClientPlayer entity) {
        setId(entity.getId());
        setPosition(entity.getPosition());
        setVelocity(entity.getVelocity());
        setName(entity.getName());
        setPlayerMoveState(entity.getCurrAnimationState());
        setDirectionState(entity.getDirectionState());
        setPlayerAttackState(entity.getCurrentAttackState());
        setCurrentSkillIndex(entity.getCurrentSkillIndex());
    }

    public Skill getCurrentSkill() {
        if (getCurrentSkillIndex() >= 0 && getCurrentSkillIndex() < allSkills.size())
            return allSkills.get(getCurrentSkillIndex());
        return null;
    }

    public Circle getShape() {
        return shape;
    }

    public void setShape(Circle shape) {
        this.shape = shape;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        radius = radius;
    }

    public PlayerMoveState getPlayerMoveState() {
        return playerMoveState;
    }

    public void setPlayerMoveState(PlayerMoveState playerMoveState) {
        this.playerMoveState = playerMoveState;
    }

    public DirectionState getDirectionState() {
        return directionState;
    }

    public void setDirectionState(DirectionState directionState) {
        this.directionState = directionState;
    }

    public PlayerAttackState getPlayerAttackState() {
        return playerAttackState;
    }

    public void setPlayerAttackState(PlayerAttackState playerAttackState) {
        this.playerAttackState = playerAttackState;
    }

    public Map<String, Skill> getSkills() {
        if (skills == null)
            skills = new ConcurrentHashMap<>();
        return skills;
    }

    public void setSkills(Map<String, Skill> skills) {
        this.skills = skills;
    }

    public int getCurrentSkillIndex() {
        return currentSkillIndex;
    }

    public void setCurrentSkillIndex(int currentSkillIndex) {
        this.currentSkillIndex = currentSkillIndex;
    }

    public DirectionState getOldDirectionState() {
        return oldDirectionState;
    }

    public void setOldDirectionState(DirectionState oldDirectionState) {
        this.oldDirectionState = oldDirectionState;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public PlayerMoveState getCurrAnimationState() {
        return currAnimationState;
    }

    public void setCurrAnimationState(PlayerMoveState currAnimationState) {
        this.currAnimationState = currAnimationState;
    }

    public PlayerAttackState getCurrentAttackState() {
        return currentAttackState;
    }

    public void setCurrentAttackState(PlayerAttackState currentAttackState) {
        this.currentAttackState = currentAttackState;
    }

    public PlayerMoveState getPrevAnimationState() {
        return prevAnimationState;
    }

    public void setPrevAnimationState(PlayerMoveState prevAnimationState) {
        this.prevAnimationState = prevAnimationState;
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

    public List<Skill> getAllSkills() {
        return allSkills;
    }

    public void setAllSkills(List<Skill> allSkills) {
        this.allSkills = allSkills;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Player))
            return false;
        if (((Player) obj).getId() != null)
            return ((Player)obj).getId().equals(getId());
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