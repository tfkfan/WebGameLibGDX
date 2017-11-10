package com.webgame.game.world.objects.impl;

import static com.webgame.game.Configs.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.state.State;
import com.webgame.game.world.objects.SkillObject;

public class FireBallObject extends SkillObject {
	protected Animation<TextureRegion> animation;
	protected TextureRegion standTexture;
	
	public FireBallObject() {
		super();
		init();
	}

	@Override
	public void initSkill(SpriteBatch batch, String spritePath) {
		loadTexture(batch, spritePath);
		setXOffset(30 / PPM);
		setYOffset(15 / PPM);

		Texture spriteTexture = getSpriteTextureLoader().getSpriteTexture();

		int h = 61;
		int w = 72;
		int l = 5;
		
		TextureRegion[] frames =  new TextureRegion[l];
		
		for(int i = 0; i < l; i++)
			frames[i] = new TextureRegion(spriteTexture, w , h, w, h);
		
		standTexture = new TextureRegion(spriteTexture, w , h, w, h);
		
		animation = new Animation<TextureRegion>(0.2f, frames);

		setRegion(standTexture);
	}

	@Override
	public State getState(){
		return currState;
	}
	
	@Override
	public TextureRegion getFrame() {

		TextureRegion region = isActive ? animation.getKeyFrame(stateTimer, true) : null;
			
		return region;
	}
	
}
