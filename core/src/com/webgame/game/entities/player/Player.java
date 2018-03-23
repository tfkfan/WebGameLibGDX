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
import com.webgame.game.server.serialization.dto.HasDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import com.webgame.game.world.common.IUpdatable;

import java.util.ArrayList;
import java.util.List;

import static com.webgame.game.Configs.PPM;

public abstract class Player extends WorldEntity implements IUpdatable {
    protected float stateTimer;

    protected DirectionState directionState;
    protected DirectionState oldDirectionState;

    protected PlayerState playerState;

    protected PlayerAnimationState currAnimationState;
    protected PlayerAnimationState prevAnimationState;

    protected transient Array<Animation<TextureRegion>> animations;
    protected transient Array<Animation<TextureRegion>> attackAnimations;
    protected transient TextureRegion[] standRegions;

    protected PlayerAttributes attributes;

    protected List<Skill> activeSkills;
    protected List<Skill> allSkills;

    protected int currentSkill;

    public Player() {
        super();
    }

    @Override
    public void init() {
        super.init();
        attributes = new PlayerAttributes();

        directionState = DirectionState.UP;
        oldDirectionState = directionState;

        playerState = PlayerState.ALIVE;

        currAnimationState = PlayerAnimationState.STAND;
        prevAnimationState = currAnimationState;

        getAttributes().setHealthPoints(1000);
        getAttributes().setMaxHealthPoints(1000);

        activeSkills = new ArrayList<>();

        currentSkill = 0;

        clearTimers();
        setBounds(0, 0, 60 / PPM, 60 / PPM);
    }


    public static Player createPlayer(World world){
        Player player = new Mage(Configs.PLAYERSHEETS_FOLDER + "/mage.png");
        player.createObject(world);
        return player;
    }

    public void castSkill(Vector2 target) {
        //checking inactive activeSkills
        List<Skill> skillsToRemove = new ArrayList<Skill>();
        for (Skill skill : activeSkills)
            if (skill.getEntityState().equals(EntityState.INACTIVE))
                skillsToRemove.add(skill);
        activeSkills.removeAll(skillsToRemove);


        Skill currSkill = allSkills.get(currentSkill);
        if (currSkill == null)
            return;

        currSkill.setStart(System.currentTimeMillis());

        Skill skill = currSkill.createCopy();
        skill.cast(target);
        activeSkills.add(skill);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (getB2body() != null)
            getB2body().setTransform(x, y, 0);
    }


    public boolean isAttackFinished() {
        Integer index = directionState.getDirIndex();
        return attackAnimations.get(index).isAnimationFinished(stateTimer);
    }

    public void clearTimers() {
        stateTimer = 0;
    }

    @Override
    public TextureRegion getFrame() {
        TextureRegion region = null;

        PlayerAnimationState currState = (PlayerAnimationState) getCurrAnimationState();

        Integer index = directionState.getDirIndex();

        switch (currState) {
            case WALK:
                region = animations.get(index).getKeyFrame(stateTimer, true);
                break;
            case ATTACK:
                region = attackAnimations.get(index).getKeyFrame(stateTimer, false);
                break;
            case STAND:
            default:
                region = standRegions[this.directionState.getDirIndex()];
                break;
        }

        return region;
    }

    @Override
    public void update(float dt) {
        if (!getCurrAnimationState().equals(PlayerAnimationState.ATTACK)
                || getCurrAnimationState().equals(PlayerAnimationState.ATTACK) && isAttackFinished()) {
            if (getVelocity().isZero())
                setCurrAnimationState(PlayerAnimationState.STAND);
            else
                setCurrAnimationState(PlayerAnimationState.WALK);
        }

        stateTimer += dt;

        if (stateTimer >= 10)
            clearTimers();
    }

    public List<Skill> getActiveSkills() {
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

    public PlayerAttributes getAttributes() {
        return attributes;
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

    public void setCurrAnimationState(PlayerAnimationState state) {
        this.currAnimationState = state;
    }

    public State getCurrAnimationState() {
        return currAnimationState;
    }

    public State getPrevAnimationState() {
        return prevAnimationState;
    }

    public void setPrevAnimationState(PlayerAnimationState prevAnimationState) {
        this.prevAnimationState = prevAnimationState;
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
            result = 31 * result + getId().intValue();

        return result;
    }
}
