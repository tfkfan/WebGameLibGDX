package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.webgame.game.state.PlayerState;
import com.webgame.game.world.objects.Player;
import static com.webgame.game.Configs.PPM;

public class DeathKnight extends Player {
	protected Array<Animation<TextureRegion>> animations;
	protected Array<Animation<TextureRegion>> attackAnimations;
	protected TextureRegion[] standRegions;

	public DeathKnight(SpriteBatch batch, String spritePath) {
		super();

		this.setSpriteBatch(batch);
		this.setSpriteTexture(spritePath);

		this.setSkill(new IceRain(batch, "skills.png"));

		setXOffset(30 / PPM);
		setYOffset(15 / PPM);

		Texture spriteTexture = getSpriteTexture();

		TextureRegion[][] frames = new TextureRegion[dirs][5];
		TextureRegion[][] attackFrames = new TextureRegion[dirs][5];
		animations = new Array<Animation<TextureRegion>>();
		attackAnimations = new Array<Animation<TextureRegion>>();
		standRegions = new TextureRegion[dirs];

		int h = 67;
		int w = 67;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++)
				frames[i][j] = new TextureRegion(spriteTexture, 5 + w * i, h * j, w, h);
			for (int j = 5; j < 9; j++)
				attackFrames[i][j - 5] = new TextureRegion(spriteTexture, 5 + w * i, h * j, w, h);
			
			standRegions[i] = new TextureRegion(spriteTexture, 5 + w * i, 0, w, h);
			attackFrames[i][4] = standRegions[i];
		}

		for (int j = 0; j < 5; j++) {
			TextureRegion tr = new TextureRegion(spriteTexture, w, h * j, w, h);
			tr.flip(true, false);
			frames[7][j] = tr;

			if (j == 0)
				standRegions[7] = tr;

			tr = new TextureRegion(spriteTexture, w * 2, h * j, w, h);
			tr.flip(true, false);
			frames[6][j] = tr;

			if (j == 0)
				standRegions[6] = tr;

			tr = new TextureRegion(spriteTexture, w * 3, h * j, w, h);
			tr.flip(true, false);
			frames[5][j] = tr;

			if (j == 0)
				standRegions[5] = tr;
		}

		for (int j = 5; j < 9; j++) {

			TextureRegion tr = new TextureRegion(spriteTexture, w, h * j, w, h);
			tr.flip(true, false);
			attackFrames[7][j - 5] = tr;

			tr = new TextureRegion(spriteTexture, w * 2, h * j, w, h);
			tr.flip(true, false);
			attackFrames[6][j - 5] = tr;

			tr = new TextureRegion(spriteTexture, w * 3, h * j, w, h);
			tr.flip(true, false);
			attackFrames[5][j - 5] = tr;
		}

		TextureRegion tr = new TextureRegion(spriteTexture, w, 0, w, h);
		tr.flip(true, false);
		attackFrames[7][4] = tr;

		tr = new TextureRegion(spriteTexture, w * 2, 0, w, h);
		tr.flip(true, false);
		attackFrames[6][4] = tr;

		tr = new TextureRegion(spriteTexture, w * 3, 0, w, h);
		tr.flip(true, false);
		attackFrames[5][4] = tr;
		
		for (int i = 0; i < dirs; i++) {
			Animation<TextureRegion> anim = new Animation<TextureRegion>(0.2f, frames[i]);
			Animation<TextureRegion> attackAnim = new Animation<TextureRegion>(0.2f, attackFrames[i]);
			animations.add(anim);
			attackAnimations.add(attackAnim);
			frames[i] = null;
			attackFrames[i] = null;
		}

		setRegion(standRegions[0]);
	}

	@Override
	public TextureRegion getFrame() {
		prevState = currState;
		currState = getState();

		TextureRegion standRegion, region;
		Integer index = getDirectionIndex();
	
		Animation<TextureRegion> animation = animations.get(index);
		Animation<TextureRegion> attackAnimation = attackAnimations.get(index);
	
		standRegion = standRegions[index];

		switch ((PlayerState) currState) {
		case WALK:
			region = animation.getKeyFrame(stateTimer, true);
			break;
		case ATTACK:
			region = attackAnimation.getKeyFrame(stateTimer, false);
			break;
		case STAND:
		default:
			region = standRegion;
			break;
		}

		return region;
	}
}
