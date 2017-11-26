package com.webgame.game.world.objects.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.webgame.game.world.objects.Player;
import com.webgame.game.world.skills.Skill;
import com.webgame.game.world.skills.impl.Blizzard;
import com.webgame.game.world.skills.impl.Explosion;
import com.webgame.game.world.skills.impl.FireBall;
import com.webgame.game.world.skills.impl.Lightning;
import com.webgame.game.world.skills.impl.MeteorRain;
import com.webgame.game.world.skills.impl.StoneRain;
import com.webgame.game.world.skills.impl.Tornado;

import static com.webgame.game.Configs.PPM;

import java.util.ArrayList;

public class Mage extends Player {

	public Mage(SpriteBatch batch, String spritePath) {
		super();

		this.setSpriteBatch(batch);
		this.setSpriteTexture(spritePath);

		Texture skillTexture = SpriteTextureLoader.loadSprite("skills.png");
		Texture skillTexture2 = SpriteTextureLoader.loadSprite("lightning.png");
		try {
			ArrayList<Skill<?>> skills = new ArrayList<Skill<?>>();
			skills.add(new Blizzard(batch, skillTexture, 30));
			skills.add(new MeteorRain(batch, skillTexture, 30));
			skills.add(new StoneRain(batch, skillTexture, 30));
			skills.add(new Explosion(batch, skillTexture));
			skills.add(new FireBall(batch, skillTexture));
			skills.add(new Lightning(batch, skillTexture2));
			skills.add(new Tornado(batch, skillTexture));
			this.setSkills(skills);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setXOffset(30 / PPM);
		setYOffset(15 / PPM);

		Texture spriteTexture = getSpriteTexture();

		TextureRegion[][] frames = new TextureRegion[dirs][5];
		TextureRegion[][] attackFrames = new TextureRegion[dirs][5];
		Array<Animation<TextureRegion>> animations = new Array<Animation<TextureRegion>>();
		Array<Animation<TextureRegion>> attackAnimations = new Array<Animation<TextureRegion>>();
		TextureRegion[] standRegions = new TextureRegion[dirs];

		int h = 61;
		int w = 75;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++)
				frames[i][j] = new TextureRegion(spriteTexture, w * i, h * j, w, h);
			for (int j = 5; j < 9; j++)
				attackFrames[i][j - 5] = new TextureRegion(spriteTexture, w * i, h * j, w, h);

			standRegions[i] = new TextureRegion(spriteTexture, w * i, 0, w, h);
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

		this.setAnimations(animations);
		this.setAttackAnimations(attackAnimations);
		this.setStandRegions(standRegions);

		setRegion(standRegions[0]);
	}

}
