package com.webgame.game.server.serialization.dto.player;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.DirectionState;
import com.webgame.game.enums.PlayerAttackState;
import com.webgame.game.enums.PlayerMoveState;
import com.webgame.game.server.serialization.dto.entity.EntityDTO;
import com.webgame.game.server.serialization.dto.UpdatableDTO;
import com.webgame.game.server.serialization.dto.skill.SkillDTO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDTO extends EntityDTO implements UpdatableDTO<Player> {
    protected String name;
    protected Vector2 position;
    protected Vector2 velocity;
    protected Vector2 target;
    protected PlayerMoveState playerMoveState;
    protected PlayerAttackState playerAttackState;
    protected DirectionState directionState;

    protected int currentSkillIndex;

    protected Map<String, SkillDTO> skills;

    public PlayerDTO() {

    }

    public PlayerDTO(String id, String name, Vector2 position) {
        setId(id);
        setName(name);
        setPosition(position);
    }

    public PlayerDTO(Player player) {
        updateBy(player);
    }

    public void updateBy(PlayerDTO playerDTO) {
        setPosition(playerDTO.getPosition());
        setVelocity(playerDTO.getVelocity());
        setTarget(playerDTO.getTarget());
        setPlayerAttackState(playerDTO.getPlayerAttackState());
        setPlayerMoveState(playerDTO.getPlayerMoveState());
        setDirectionState(playerDTO.getDirectionState());
        setCurrentSkillIndex(playerDTO.getCurrentSkillIndex());
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Map<String, SkillDTO> getSkills() {
        if (skills == null)
            skills = new ConcurrentHashMap<>();
        return skills;
    }

    public void setSkills(Map<String, SkillDTO> skills) {
        this.skills = skills;
    }

    public int getCurrentSkillIndex() {
        return currentSkillIndex;
    }

    public void setCurrentSkillIndex(int currentSkillIndex) {
        this.currentSkillIndex = currentSkillIndex;
    }

    @Override
    public void updateBy(Player entity) {
        setId(entity.getId());
        setPosition(entity.getPosition());
        setVelocity(entity.getVelocity());
        setName(entity.getAttributes().getName());
        setPlayerMoveState(entity.getCurrAnimationState());
        setDirectionState(entity.getDirectionState());
        setPlayerAttackState(entity.getCurrentAttackState());
        setTarget(entity.getTarget());
        setCurrentSkillIndex(entity.getCurrentSkillIndex());
    }
}
