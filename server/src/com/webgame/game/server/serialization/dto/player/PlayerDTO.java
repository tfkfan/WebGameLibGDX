package com.webgame.game.server.serialization.dto.player;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.DirectionState;
import com.webgame.game.enums.PlayerAttackState;
import com.webgame.game.enums.PlayerMoveState;
import com.webgame.game.server.serialization.dto.UpdatableDTO;

public class PlayerDTO implements UpdatableDTO<Player> {
    protected long id;
    protected String name;
    protected Vector2 position;
    protected Vector2 velocity;
    protected Vector2 target;
    protected PlayerMoveState playerMoveState;
    protected PlayerAttackState playerAttackState;
    protected DirectionState directionState;

    protected int currentSkillIndex;

    public PlayerDTO(){

    }

    public PlayerDTO(Player player){
        updateBy(player);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
