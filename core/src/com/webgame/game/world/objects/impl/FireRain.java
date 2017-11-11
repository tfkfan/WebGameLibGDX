package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.objects.Skill;
import com.webgame.game.world.objects.SkillObject;

public class FireRain extends Skill<FireRainObject> {

	float stateTimer;
	int index;

	public FireRain(SpriteBatch batch, String spritePath) {
		super(batch, spritePath);
		initSkill(10);
		stateTimer = 0;
		index = 0;
	}

	@Override
	public FireRainObject createObject() {
		FireRainObject obj = new FireRainObject();
		obj.setFalling(true);
		return obj;
	}

	@Override
	public FireRainObject[] createObjectsArray(Integer num) {
		return new FireRainObject[num];
	}

	@Override
	public void animateSkill(float dt) {
		super.animateSkill(dt);

		if (!isActive)
			return;
		
		if (index != -1) {
			stateTimer += dt;
			if (stateTimer >= dt *20) {
				SkillObject obj = skillObjects[index];
				obj.updateDistance();
				obj.setActive(true);
				
				if (index < skillObjectsNum - 1)
					index++;
				else
					index = -1;
				stateTimer = 0;
			}
		}

		for (int i = 0; i < skillObjectsNum; i++) {
			SkillObject obj = skillObjects[i];
			if (obj.isActive()){
				obj.move(dt);
				obj.draw(batch);
			}
		}

	}

}
