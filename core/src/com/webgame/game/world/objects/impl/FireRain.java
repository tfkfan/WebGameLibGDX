package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.objects.Skill;
import com.webgame.game.world.objects.SkillObject;

public class FireRain extends Skill<FireRainObject> {

	float stateTimer;
	
	int index;

	public FireRain(SpriteBatch batch, String spritePath) {
		super(batch, spritePath);
		createSkill(10);
		stateTimer = 0;
		index = 0;
	}

	@Override
	public FireRainObject createObject() {
		FireRainObject obj = new FireRainObject();
		obj.setFalling(true);
		obj.rotate(-150);
		return obj;
	}

	@Override
	public FireRainObject[] createObjectsArray(Integer num) {
		return new FireRainObject[num];
	}

	@Override
	public void customAnimation(float dt) {
		if (!isActive)
			return;
		
		if(skillTimer >= 10){
			isActive = false;
			for (int i = 0; i < skillObjectsNum; i++) {
				SkillObject obj = skillObjects[i];
				obj.setActive(false);
				index = 0;
			}
			return;
		}
		
		skillTimer += dt;
		
		if (index != -1) {
			stateTimer += dt;
			if (stateTimer >= dt *30) {
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
		
		if(stateTimer >= dt*30)
			stateTimer = 0;

		for (int i = 0; i < skillObjectsNum; i++) {
			SkillObject obj = skillObjects[i];
			if (obj.isActive()){
				obj.move(dt);
				obj.draw(batch);
			}
		}

	}

}
