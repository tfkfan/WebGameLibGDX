package com.webgame.game.entities.player.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.webgame.game.Configs;
import com.webgame.game.animation.impl.*;
import com.webgame.game.entities.skill.*;
import com.webgame.game.enums.DirectionState;
import com.webgame.game.enums.FrameSizes;
import com.webgame.game.utils.GameUtils;
import com.webgame.game.entities.player.Player;

import static com.webgame.game.Configs.PPM;

import java.util.ArrayList;
import java.util.List;

public class Mage extends Player {

    public Mage(SpriteBatch batch, String spritePath) {
        super();
        int dirs = DirectionState.values().length;
        try {
            float[] animSizes1 = {FrameSizes.BLIZZARD.getW(), FrameSizes.BLIZZARD.getH()};
            float[] standSizes1 = {FrameSizes.BLIZZARD.getW(), FrameSizes.BLIZZARD.getH()};

            List<Skill> allSkills = new ArrayList<Skill>();

            Texture standSkillTexture = GameUtils.loadSprite(Configs.SKILLSHEETS_FOLDER + "/fire_002.png");
            Texture animSkillTexture = GameUtils.loadSprite(Configs.SKILLSHEETS_FOLDER + "/s001.png");
            TextureRegion standTexture = new TextureRegion(standSkillTexture, 0, 0, standSkillTexture.getWidth(), standSkillTexture.getHeight());
            Skill skill1 = new SingleSkill(this, standTexture, new FlameAnimation(animSkillTexture));
            skill1.setDamage(150);
            skill1.setCooldown(GameUtils.calcTime(3,0));

            allSkills.add(skill1);

            Texture skillsTexture =  GameUtils.loadSprite(Configs.SKILLSHEETS_FOLDER + "/skills.png");
            TextureRegion blizzardStandTexture = new TextureRegion(skillsTexture, 5, 245, 30, 30);
            Skill skill2 = new FallingSkill(this, blizzardStandTexture, new BlizzardFragmentAnimation(skillsTexture), standSizes1, animSizes1);
            skill2.setCooldown(GameUtils.calcTime(10,0));
            skill2.setDamage(1);
            allSkills.add(skill2);

            Texture skill3Texture =  GameUtils.loadSprite(Configs.SKILLSHEETS_FOLDER + "/cast_001.png");
            Skill skill3 = new BuffSkill(this, null, new BuffAnimation(skill3Texture));
            allSkills.add(skill3);


            setAllSkills(allSkills);

            /*
            Texture skillsTexture =  SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/activeSkills.png");
            TextureRegion blizzardStandTexture = new TextureRegion(skillsTexture, 5, 245, 30, 30);
            SkillOrig s6 = skillFactory.createFallingAOESkill(BlizzardFragmentAnimation.class, batch,
                    skillsTexture, blizzardStandTexture, 30);
            s6.getSkillState().setTitle("Blizzard");

            */
            /////////////////////
            /*

            ArrayList<SkillOrig<?>> skillOrigs = new ArrayList<SkillOrig<?>>();

            ISkillFactory skillFactory = new SkillFactory();

            SkillOrig s1 = skillFactory.createBuffSkill(BuffAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/cast_005.png"));
            s1.getSkillState().setTitle("Buff1");
            skillOrigs.add(s1);

            TextureRegion iceStandTexture = new TextureRegion(SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/ice_003.png"));
            SkillOrig ss2 = skillFactory.createSingleSkill(IceBlastAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/s7.png"),
                    iceStandTexture);
            ss2.getSkillState().setDamage(50d);
            skillOrigs.add(ss2);


            TextureRegion fireStandTexture = new TextureRegion(SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/fire_002.png"));
            SkillOrig ss3 = skillFactory.createSingleSkill(FireBlastAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/activeSkills.png"),
                    fireStandTexture);
            ss3.getSkillState().setDamage(50d);
            skillOrigs.add(ss3);

            SkillOrig s2 = skillFactory.createBuffSkill(BuffAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/cast_006.png"));
            s2.getSkillState().setTitle("Buff2");
            skillOrigs.add(s2);

            SkillOrig s3 = skillFactory.createBuffSkill(BuffAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/cast_007.png"));
            s3.getSkillState().setTitle("Buff3");
            skillOrigs.add(s3);

            SkillOrig s4 = skillFactory.createBuffSkill(BuffAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/cast_008.png"));
            s4.getSkillState().setTitle("Buff4");
            skillOrigs.add(s4);

            SkillOrig s5 = skillFactory.createBuffSkill(BuffAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/cast_009.png"));
            s5.getSkillState().setTitle("Buff5");
            skillOrigs.add(s5);



            SkillOrig s7 = skillFactory.createStaticTimedAOESkill(MagicShieldAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/s2.png"));
            s7.getSkillState().setTitle("Some skill");
            skillOrigs.add(s7);

            SkillOrig s = skillFactory.createBuffSkill(HealAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/s6.png"));
            s.getSkillState().setTitle("heal");
            s.getSkillState().setHeal(true);
            s.getSkillState().setHealHP(50d);
            skillOrigs.add(s);
            */
            /*
            skillOrigs.add(skillFactory.createStaticSingleAOESkill(MagicSourceAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/s9.png")));
            skillOrigs.add(skillFactory.createStaticSingleAOESkill(MagicBuffAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/s5.png")));
            skillOrigs.add(skillFactory.createStaticSingleAOESkill(IceBlastAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/s7.png")));
            skillOrigs.add(skillFactory.createStaticSingleAOESkill(MagicBuffAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/s3.png")));
            skillOrigs.add(skillFactory.createStaticSingleSkill(LightningAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/lightning.png")));
            skillOrigs.add(skillFactory.createStaticTimedAOESkill(TornadoAnimation.class, batch,
                    SpriteTextureLoader.loadSprite(Configs.SKILLSHEETS_FOLDER + "/skillOrigs.png")));
                    */
            // this.setSkills(skillOrigs);
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        setXOffset(30 / PPM);
        setYOffset(15 / PPM);

        Texture spriteTexture = GameUtils.loadSprite(spritePath);

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