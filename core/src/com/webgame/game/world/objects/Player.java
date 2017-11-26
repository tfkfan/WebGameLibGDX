package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.webgame.game.state.Direction;
import com.webgame.game.state.PlayerState;
import com.webgame.game.state.State;
import com.webgame.game.world.skills.Skill;
import static com.webgame.game.Configs.PPM;

import java.util.List;

public abstract class Player extends WorldGameObject {
	protected Integer healthPoints;
	protected Integer manaPoints;

	protected Integer maxHealthPoints;
	protected Integer maxManaPoints;

	protected String playerName;
	protected Integer level;

	protected List<Skill<?>> skills;
	protected Integer currentSkillIndex;

	protected boolean isAnimated;
	protected boolean attackAnimation;
	protected boolean isAlive;
	protected float attackTimer;
	protected float stateTimer;
	protected final float attackLimit = 0.8f;

	protected Direction direction;
	protected Direction oldDirection;

	protected State currState;
	protected State prevState;

	protected Array<Animation<TextureRegion>> animations;
	protected Array<Animation<TextureRegion>> attackAnimations;
	protected TextureRegion[] standRegions;

	public Player() {
		super();
		init();
	}

	@Override
	public void init() {
		super.init();
		isAlive = true;
		isAnimated = false;
		attackAnimation = false;

		direction = Direction.UP;
		oldDirection = direction;

		currState = PlayerState.STAND;
		prevState = currState;

		setHealthPoints(1000);
		setMaxHealthPoints(1000);

		stateTimer = attackTimer = 0;
		setCurrentSkillIndex(0);
		setBounds(0, 0, 60 / PPM, 60 / PPM);
	}

	@Override
	public void createObject(World world, boolean isSensor) {
		setWorld(world);

		BodyDef bdef = new BodyDef();
		bdef.position.set(0, 0);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		fdef.isSensor = isSensor;
		CircleShape shape = new CircleShape();

		shape.setRadius(20 / PPM);

		fdef.shape = shape;
		b2body.createFixture(fdef);
	}

	public void attack(float targetX, float targetY) {
		Skill<?> skill = getCurrentSkill();
		if (skill == null || skill.isActive())
			return;

		setState(PlayerState.ATTACK, true);
		attackAnimation = true;
		skill.cast(new Vector2(getX(), getY()), new Vector2(targetX, targetY));
	}

	public Integer getCurrentSkillIndex() {
		return currentSkillIndex;
	}

	public void setCurrentSkillIndex(Integer currentSkillIndex) {
		this.currentSkillIndex = currentSkillIndex;
	}

	public void playerCollision(Player player) {
		Skill<?> skill = player.getCurrentSkill();
		if (skill == null || !skill.isActive())
			return;

		skill.skillCollision(this);
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
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

	public TextureRegion[] getStandRegions() {
		return standRegions;
	}

	public void setStandRegions(TextureRegion[] standRegions) {
		this.standRegions = standRegions;
	}

	public Integer getDirectionIndex() {
		Integer index = 0;
		switch (direction) {
		case UP:
			index = 0;
			break;
		case UPRIGHT:
			index = 1;
			break;
		case RIGHT:
			index = 2;
			break;
		case RIGHTDOWN:
			index = 3;
			break;
		case DOWN:
			index = 4;
			break;
		case LEFTDOWN:
			index = 5;
			break;
		case LEFT:
			index = 6;
			break;
		case UPLEFT:
			index = 7;
			break;
		default:
			index = 0;
			break;
		}
		return index;
	}

	public void updateDirection() {
		oldDirection = direction;

		if (velocity.x > 0)
			direction = Direction.RIGHT;
		else if (velocity.x < 0)
			direction = Direction.LEFT;

		if (velocity.y > 0)
			direction = Direction.UP;
		else if (velocity.y < 0)
			direction = Direction.DOWN;

		if (velocity.x > 0 && velocity.y > 0)
			direction = Direction.UPRIGHT;
		else if (velocity.x > 0 && velocity.y < 0)
			direction = Direction.RIGHTDOWN;
		else if (velocity.x < 0 && velocity.y > 0)
			direction = Direction.UPLEFT;
		else if (velocity.x < 0 && velocity.y < 0)
			direction = Direction.LEFTDOWN;
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

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public List<Skill<?>> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill<?>> skills) {
		this.skills = skills;
	}

	public Skill<?> getCurrentSkill() {
		if (skills == null || currentSkillIndex < 0 || currentSkillIndex >= skills.size())
			return null;
		return skills.get(currentSkillIndex);
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public void update(float dt) {
		updateDirection();

		if (oldDirection != direction || currState != prevState || stateTimer > 1000)
			stateTimer = 0;

		if (attackAnimation)
			attackTimer += dt;
		if (attackTimer >= attackLimit) {
			attackAnimation = false;
			attackTimer = 0;
		}

		stateTimer += dt;

		super.update(dt);
	}

	public void animateSkills(float dt) {
		if (skills == null)
			return;

		for (Skill<?> skill : skills)
			if (skill != null && skill.isActive())
				skill.animateSkill(dt);
	}

	@Override
	public void drawShape(ShapeRenderer sr) {
		sr.setColor(Color.BLUE);
		sr.set(ShapeType.Line);

		super.drawShape(sr);

		Skill<?> skill = getCurrentSkill();
		if (skill != null)
			skill.drawShape(sr);
		sr.set(ShapeType.Filled);
		sr.setColor(Color.GREEN);
		sr.rect(this.getX(), this.getY() + getHeight() + 5 / PPM,
				(getHealthPoints() / (float) getMaxHealthPoints()) * getWidth(), 5 / PPM);
	}

	@Override
	public TextureRegion getFrame() {
		PlayerState currState = getState();
		TextureRegion region;
		Integer index = getDirectionIndex();

		switch (currState) {
		case WALK:
			region = animations.get(index).getKeyFrame(stateTimer, true);
			break;
		case ATTACK:
			region = attackAnimations.get(index).getKeyFrame(stateTimer, false);
			break;
		case STAND:
		default:
			region = standRegions[index];
			break;
		}

		return region;
	}

	public Rectangle getPlayerRectangle() {
		return new Rectangle(this.getX(), this.getY(), getWidth(), getHeight());
	}

	public State getPrevState() {
		return prevState;
	}

	public PlayerState getState() {
		if (velocity.x != 0 || velocity.y != 0)
			setState(PlayerState.WALK, true);
		else
			setState(PlayerState.STAND, true);

		if (attackAnimation)
			setState(PlayerState.ATTACK, false);

		return (PlayerState) currState;

	}

	public void setState(State state, boolean swap) {
		if (swap)
			this.prevState = this.currState;
		currState = state;
	}

	public float getStateTimer() {
		return stateTimer;
	}
}
