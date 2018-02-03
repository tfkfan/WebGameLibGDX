package com.webgame.game.world.player.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.webgame.game.Configs;
import com.webgame.game.utils.SpriteTextureLoader;
import com.webgame.game.world.common.factory.ISkillFactory;
import com.webgame.game.world.common.factory.impl.SkillFactory;
import com.webgame.game.world.player.Player;
import com.webgame.game.world.skills.Skill;
import com.webgame.game.world.skills.skillsprites.impl.*;

import static com.webgame.game.Configs.PPM;

import java.util.ArrayList;

public class Mage extends Player {

    public Mage(SpriteBatch batch, String spritePath) {
        super();

        try {
            ArrayList<Skill<?>> skills = new ArrayList<Skill<?>>();

            ISkillFactory skillFactory = new SkillFactory();

            skills.add(skillFactory.createStaticSingleAOESkill(BuffSprite.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/cast_005.png")));
            skills.add(skillFactory.createStaticSingleAOESkill(BuffSprite.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/cast_006.png")));
            skills.add(skillFactory.createStaticSingleAOESkill(BuffSprite.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/cast_007.png")));
            skills.add(skillFactory.createStaticSingleAOESkill(BuffSprite.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/cast_008.png")));
            skills.add(skillFactory.createStaticSingleAOESkill(BuffSprite.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/cast_009.png")));

            /*
            Skill s1 = skillFactory.createFallingAOESkill(BlizzardSprite.class, batch, skillTexture, 30);
            s1.setDamage(5d);
            skills.add(s1);
            Skill s2 = skillFactory.createStaticSingleAOESkill(BuffSprite.class, batch, skillTexture5);
            s2.setTimed(false);
            skills.add(s2);

            Skill s3 = skillFactory.createStaticSingleAOESkill(HealSprite.class, batch, skillTexture6);

            s3.setTimed(false);
            skills.add(s3);

            Skill s4 = skillFactory.createStaticSingleAOESkill(MagicSourceSprite.class, batch, skillTexture7);
            //s3.setDamage(5d);
            skills.add(s4);

            Skill s5 = skillFactory.createStaticSingleAOESkill(MagicShieldSprite.class, batch, skillTexture8);
            //s3.setDamage(5d);
            skills.add(s5);

            Skill s6 = skillFactory.createStaticSingleAOESkill(IceExplosionSprite.class, batch, skillTexture3);
            s6.setDamage(70d);
            skills.add(s6);

            Skill s7 = skillFactory.createStaticSingleSkill(MagicBuffSprite.class, batch, skillTexture4);
            s7.setDamage(0d);
            skills.add(s7);

            Skill s8 = skillFactory.createStaticSingleSkill(LightningSprite.class, batch, skillTexture2);
            s8.setDamage(30d);
            skills.add(s8);

            Skill s9 = skillFactory.createStaticSingleAOESkill(TornadoSprite.class, batch, skillTexture);
            s9.setDamage(3d);
            s9.setTimed(true);
            skills.add(s9);

            */
            this.setSkills(skills);
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        setXOffset(30 / PPM);
        setYOffset(15 / PPM);

        Texture spriteTexture = SpriteTextureLoader.loadSprite(spritePath);

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
