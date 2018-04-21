package com.webgame.common.server.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.common.client.entities.skill.ClientSkill;
import com.webgame.common.client.enums.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.webgame.common.client.Configs.PPM;

public abstract class Player<T extends Skill>  extends AnimatedEntity {
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

    protected List<T> allSkills;
    protected Map<String, T> skills;

    protected float radius = 20 / PPM;

    protected String spritePath;

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

    public Player(Player player) {
        updateBy(player);
    }

    public void updateBy(Player playerDTO) {
        setAllSkills(new ArrayList<>(playerDTO.getAllSkills()));
        setHealthPoints(playerDTO.getHealthPoints());
        setMaxHealthPoints(playerDTO.getMaxHealthPoints());

        setPosition(playerDTO.getPosition());
        setVelocity(playerDTO.getVelocity());
        setPlayerAttackState(playerDTO.getPlayerAttackState());
        setPlayerMoveState(playerDTO.getPlayerMoveState());
        setDirectionState(playerDTO.getDirectionState());
        setCurrentSkillIndex(playerDTO.getCurrentSkillIndex());
    }

    public Skill castSkill(Vector2 target, Vector2 velocity, String id, Rectangle area) {
        ClientSkill currSkill = (ClientSkill) getCurrentSkill();
        if (currSkill == null)
            return null;

       /* Long end = getCurrentSkill().getStart() + getCurrentSkill().getCooldown();
        Long currentTime = System.currentTimeMillis();

        if (currentTime < end)
           return null;

        currSkill.setStart(System.currentTimeMillis());*/

        final T skill = currSkill.createCopy();
        skill.setArea(area);
        skill.setVelocity(velocity);
        skill. setTarget(target);
        skill.setMoveState(MoveState.MOVING);
        skill.setEntityState(EntityState.ACTIVE);
        skill.setMarkState(MarkState.UNMARKED);
        skill.setId(id);
        skill.setPosition(getPosition());

        Vector2 newPos = new Vector2();
        newPos.x = target.x - skill.getArea().getWidth() / 2;
        newPos.y = target.y - skill.getArea().getHeight() / 2;
        skill.getArea().setPosition(newPos);

        skills.put(id, skill);
        return skill;
    }

    public T getCurrentSkill() {
        if (getCurrentSkillIndex() >= 0 && getCurrentSkillIndex() < allSkills.size())
            return allSkills.get(getCurrentSkillIndex());
        return null;
    }

    public String getSpritePath() {
        return spritePath;
    }

    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
    }

    public Circle getShape() {
        return new Circle(getPosition(), getRadius());
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

    public Map<String, T> getSkills() {
        if (skills == null)
            skills = new HashMap<>();
        return skills;
    }

    public void setSkills(Map<String, T> skills) {
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

    public List<T> getAllSkills() {
        return allSkills;
    }

    public void setAllSkills(List<T> allSkills) {
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
