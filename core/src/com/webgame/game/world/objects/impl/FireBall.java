package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.objects.Skill;
import com.webgame.game.world.objects.SkillObject;

public class FireBall extends Skill {

	public FireBall(SpriteBatch batch, String spritePath) {
		super(batch, spritePath);
		initSkill();
	}

	@Override
	public void initSkill() {
		Integer num = 1;
		try {
			setSkillObjectsNum(num);
			
			SkillObject[] objs = new FireBallObject[1];
			objs[0].initSkill(batch, spritePath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
