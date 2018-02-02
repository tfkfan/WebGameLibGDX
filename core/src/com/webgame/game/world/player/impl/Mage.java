package com.webgame.game.world.player.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.webgame.game.Configs;
import com.webgame.game.utils.SpriteTextureLoader;
import com.webgame.game.world.player.Player;
import com.webgame.game.world.skills.Skill;
import com.webgame.game.world.skills.impl.skills.*;

import static com.webgame.game.Configs.PPM;

import java.util.ArrayList;

public class Mage extends Player {

	public Mage(SpriteBatch batch, String spritePath) {
		super();
		Texture skillTexture = SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/skills.png");
		Texture skillTexture2 = SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER +"/lightning.png");
		Texture skillTexture3 = SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER +"/s7.png");
		Texture skillTexture4 = SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER +"/s5.png");
		Texture skillTexture5 = SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER +"/cast_005.png");
		Texture skillTexture6 = SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER +"/s6.png");
		Texture skillTexture7 = SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER +"/s9.png");
		Texture skillTexture8 = SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER +"/s2.png");
		try {
			ArrayList<Skill<?>> skills = new ArrayList<Skill<?>>();



			Teleport s2 = new Teleport(batch, skillTexture5);
			s2.setDamage(5d);
			skills.add(s2);

			Heal s3 = new Heal(batch, skillTexture6);
			//s3.setDamage(5d);
			skills.add(s3);

			MagicSource s4 = new MagicSource(batch, skillTexture7);
			//s3.setDamage(5d);
			skills.add(s4);

			MagicShield s5 = new MagicShield(batch, skillTexture8);
			//s3.setDamage(5d);
			skills.add(s5);

			/*

			Blizzard b = new Blizzard(batch, skillTexture, 30);
			b.setDamage(5d);
			skills.add(b);
			MeteorRain mr = new MeteorRain(batch, skillTexture, 30);
			mr.setDamage(5d);
			
			skills.add(mr);
			
			StoneRain sr = new StoneRain(batch, skillTexture, 30);
			sr.setDamage(5d);
			skills.add(sr);
			
			Explosion e = new Explosion(batch, skillTexture);
			e.setDamage(50d);
			skills.add(e);
			
			FireBall fb = new FireBall(batch, skillTexture);
			fb.setDamage(100d);
			skills.add(fb);
			*/
			Lightning l = new Lightning(batch, skillTexture2);
			l.setDamage(30d);
			skills.add(l);
			
			Tornado t = new Tornado(batch, skillTexture);
			t.setDamage(3d);
			skills.add(t);

			IceExplosion ie = new IceExplosion(batch, skillTexture3);
			ie.setDamage(70d);
			skills.add(ie);

			MagicBuff mb = new MagicBuff (batch, skillTexture4);
			ie.setDamage(0d);
			skills.add(mb);
			this.setSkills(skills);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		setXOffset(30 / PPM);
		setYOffset(15 / PPM);

		Texture spriteTexture =SpriteTextureLoader.loadSprite(spritePath);

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
	}
}
