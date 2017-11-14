package com.webgame.game.world.objects;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.world.objects.impl.ExplosionObject;

public abstract class StaticAOESkill<T extends SkillObject> extends Skill<T> {
	protected final float skillDuration = 10;
	protected int index;
	final int w = 50;
	final int h = 50;

	public StaticAOESkill(SpriteBatch batch, Texture spriteTexture, Integer numFrames) throws Exception {
		super(batch, spriteTexture, numFrames);
		this.setArea(new Rectangle(0,0,w/PPM, h/PPM));
		index = 0;
	}

	@Override
	public void customAnimation(float dt) {
		if (skillTimer >= skillDuration) {
			isActive = false;
			for (int i = 0; i < numFrames; i++) {
				SkillObject obj = skillObjects.get(i);
				obj.setActive(false);
				obj.setStatic(true);
				obj.setAOE(true);
				obj.setPosition(0, 0);
				obj.setFinalAnimated(false);

			}
			index = 0;
			clearTimers();
			return;
		}
		if(isActive)
			skillTimer += dt;
		for (int i = 0; i < numFrames; i++) {
			SkillObject obj = skillObjects.get(i);
			if (obj.isActive()) {
				obj.update(dt);
				obj.draw(batch);
			}
		}
	}
	

	@Override
	protected void initFrame(T frame, Vector2 playerPosition, Vector2 targetPosition) {
		// TODO Auto-generated method stub
		
	}
}
