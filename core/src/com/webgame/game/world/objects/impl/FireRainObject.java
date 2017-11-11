package com.webgame.game.world.objects.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.state.State;
import com.webgame.game.world.objects.SkillObject;

public class FireRainObject extends SkillObject {
	protected Animation<TextureRegion> animation;
	protected TextureRegion standTexture;
	
	public FireRainObject() {
		super();
	}

	@Override
	public void initSkill(SpriteBatch batch, String spritePath) {
		this.setSpriteBatch(batch);
		this.setSpriteTexture(spritePath);

		Texture spriteTexture = getSpriteTexture();

		int h = 50;
		int w = 30;
		int l = 1;
		
		TextureRegion[] frames =  new TextureRegion[l];
		
		//Доделать
		for(int i = 0; i < l; i++)
			frames[i] = new TextureRegion(spriteTexture, 200, 155, w, h);
		
		standTexture = new TextureRegion(spriteTexture,810, 50, w, h);
		
		animation = new Animation<TextureRegion>(0.2f, frames);

		int w2 = 30;
		int h2 = 50;
		this.setBounds(0,0, w2/PPM, h2/PPM);
		setRegion(standTexture);
	}

	@Override
	public State getState(){
		return currState;
	}
	
	@Override
	public TextureRegion getFrame() {
		TextureRegion region = isActive ? standTexture : null;
			
		return region;
	}

	@Override
	public void afterMove() {
		super.afterMove();
	}
	
}
