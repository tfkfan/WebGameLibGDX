package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.objects.FallingAOESkill;

public class FireRain extends FallingAOESkill<FireRainObject> {

	float stateTimer;

	int index;

	public FireRain(SpriteBatch batch, String spritePath) {
		super(batch, spritePath);
		createSkill(50);
		stateTimer = 0;
		index = 0;
	}

	@Override
	public FireRainObject createObject() {
		FireRainObject obj = new FireRainObject();
		obj.setFalling(true);
		// obj.rotate(-150);
		return obj;
	}

}
