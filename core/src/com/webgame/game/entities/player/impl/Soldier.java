package com.webgame.game.entities.player.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.webgame.game.enums.DirectionState;
import com.webgame.game.utils.GameUtils;
import com.webgame.game.entities.player.Player;
import static com.webgame.game.Configs.PPM;

@Deprecated
public class Soldier extends Player {
	public Soldier(SpriteBatch batch, String spritePath) {
		super();

		int dirs = DirectionState.values().length;
		//this.setSkillOrig(new IceRain(batch, "skills.png"));

		setXOffset(30 / PPM);
		setYOffset(15 / PPM);
		Texture spriteTexture = GameUtils.loadSprite(spritePath);

		TextureRegion[][] frames = new TextureRegion[dirs][5];
		TextureRegion[][] attackFrames = new TextureRegion[dirs][5];
		Array<Animation<TextureRegion>> animations = new Array<Animation<TextureRegion>>();
		Array<Animation<TextureRegion>> attackAnimations = new Array<Animation<TextureRegion>>();
		TextureRegion[] standRegions = new TextureRegion[dirs];

		int h =52;
		int w = 75;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++)
				frames[i][j] = new TextureRegion(spriteTexture, w * i, 5 + h * j, w, h);
			for (int j = 5; j < 9; j++)
				attackFrames[i][j - 5] = new TextureRegion(spriteTexture,  w * i, h * j, w, h);
			
			standRegions[i] = new TextureRegion(spriteTexture,  w * i, 0, w, h);
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
		
		this.setAnimations(attackAnimations);
		this.setAttackAnimations(attackAnimations);
		this.setStandRegions(standRegions);
	}

}
