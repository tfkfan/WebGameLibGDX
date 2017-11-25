package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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

public abstract class Player extends WorldGameObject {
	protected Integer healthPoints;
	protected Integer manaPoints;

	protected Integer maxHealthPoints;
	protected Integer maxManaPoints;

	protected String playerName;
	protected Integer level;

	protected Skill<?> skill;

	protected boolean isAnimated;
	protected boolean attackAnimation;
	protected boolean isAlive;
	protected float attackTimer;
	protected final float attackLimit = 0.8f;

	protected Direction direction;
	protected Direction oldDirection;
	
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
		oldDirection = Direction.UP;

		prevState = PlayerState.STAND;
		currState = PlayerState.STAND;

		this.setHealthPoints(1000);
		this.setMaxHealthPoints(1000);

		attackTimer = 0;
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
		if (skill.isActive())
			return;
		this.prevState = this.currState;
		this.currState = PlayerState.ATTACK;
		this.attackAnimation = true;
		skill.cast(new Vector2(getX(), getY()), new Vector2(targetX, targetY));
	}

	public void playerCollision(Player player) {
		Skill<?> skill = player.getSkill();
		if (!skill.isActive())
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

		if (oldDirection != direction || currState != prevState)
			stateTimer = 0;
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

	public Skill<?> getSkill() {
		return skill;
	}

	public void setSkill(Skill<?> skill) {
		this.skill = skill;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public void update(float dt) {
		this.updateDirection();
		if (attackAnimation)
			attackTimer += dt;
		if (attackTimer >= attackLimit) {
			attackAnimation = false;
			attackTimer = 0;
		}
		super.update(dt);
	}

	public void animateSkills(float dt) {
		if (skill != null && skill.isActive())
			skill.animateSkill(dt);
	}

	@Override
	public void drawShape(ShapeRenderer sr) {
		sr.setColor(Color.BLUE);
		sr.set(ShapeType.Line);

		super.drawShape(sr);
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

		TextureRegion standRegion, region;
		Integer index = getDirectionIndex();

		Animation<TextureRegion> animation = animations.get(index);
		Animation<TextureRegion> attackAnimation = attackAnimations.get(index);

		standRegion = standRegions[index];

		switch (currState) {
		case WALK:
			region = animation.getKeyFrame(stateTimer, true);
			break;
		case ATTACK:
			region = attackAnimation.getKeyFrame(stateTimer, false);
			break;
		case STAND:
		default:
			region = standRegion;
			break;
		}

		return region;
	}

	public Rectangle getPlayerRectangle() {
		return new Rectangle(this.getX(), this.getY(), getWidth(), getHeight());
	}

	@Override
	public PlayerState getState() {
		if (velocity.x != 0 || velocity.y != 0)
			setState(PlayerState.WALK);
		else
			setState(PlayerState.STAND);

		if (attackAnimation)
			setState(PlayerState.ATTACK);

		return (PlayerState) currState;

	}

	public void setState(State state) {
		this.prevState = this.currState;
		currState = state;
	}
}
