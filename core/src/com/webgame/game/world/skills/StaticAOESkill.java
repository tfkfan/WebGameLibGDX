package com.webgame.game.world.skills;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public abstract class StaticAOESkill<T extends SkillSprite> extends Skill<T> {
	final int w = 100;
	final int h = 100;

	protected Vector2 vel = new Vector2(0, 0);

	public StaticAOESkill(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
		super(batch, spriteTexture, numFrames);
		this.isAOE = true;
		this.isStatic = true;
		this.setArea(new Rectangle(0, 0, w / PPM, h / PPM));
	}

	@Override
	protected void initFrame(T frame, Vector2 playerPosition, Vector2 targetPosition) {
		frame.setVelocity(vel);
		frame.setFinalAnimated(false);
		frame.setAnimateTimer(0);
		area.setPosition(targetPosition.x - area.getWidth() / 2, targetPosition.y - area.getHeight() / 2);
		initPositions(frame);
	}

	protected void initPositions(T frame) {
		float x = area.getX() - (frame.getWidth() - area.getWidth()) / 2;
		float y = area.getY() - (frame.getHeight() - area.getHeight()) / 2;
		frame.updateDistance();
		frame.setAnimateTimer(0);
		frame.setActive(true);
		frame.setPosition(x, y);
	}

	@Override
	protected void updateFrame(T frame, float dt) {
		super.updateFrame(frame, dt);
		frame.updateDistance();
		frame.setAnimateTimer(frame.getAnimateTimer() + dt);
	}

	@Override
	protected void afterCustomAnimation() {
		if(isTimed)
			return;

		SkillSprite obj = getSkillObjects().get(0);
		if (obj.getAnimateTimer() > obj.getAnimationMaxDuration())
			isActive = false;
	}
}
