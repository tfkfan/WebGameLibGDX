package com.webgame.game.world.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.objects.impl.SpriteTextureLoader;

public abstract class SingleSkill<T extends SkillObject> extends Skill<T> {

	public SingleSkill(SpriteBatch batch, String spritePath) {
		super(batch, SpriteTextureLoader.loadSprite(spritePath));
	}

	public SingleSkill(SpriteBatch batch, Texture spriteTexture) {
		super(batch, spriteTexture);
	}

	@Override
	public void customAnimation(float dt) {
		for (int i = 0; i < skillObjectsNum; i++) {
			SkillObject obj = skillObjects.get(i);
			if (obj.isActive()) {
				obj.update(dt);
				obj.draw(batch);
			}
		}

	}

}
