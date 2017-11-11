package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.objects.FallingAOESkill;

public class StoneRain extends FallingAOESkill<StoneRainObject> {

	public StoneRain(SpriteBatch batch, String spritePath) {
		super(batch, spritePath);
		createSkill(10);
	}

	@Override
	public StoneRainObject createObject() {
		StoneRainObject obj = new StoneRainObject();
		obj.setFalling(true);
		// obj.rotate(-150);
		return obj;
	}

}
