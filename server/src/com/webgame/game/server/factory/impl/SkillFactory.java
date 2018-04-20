package com.webgame.game.server.factory.impl;

import com.webgame.game.Configs;
import com.webgame.game.entities.skill.impl.BuffClientSkill;
import com.webgame.game.entities.skill.impl.FallingClientSkill;
import com.webgame.game.entities.skill.impl.SingleClientSkill;
import com.webgame.game.entities.skill.impl.StaticClientSkill;
import com.webgame.game.enums.FrameSizes;
import com.webgame.game.enums.SkillKind;
import com.webgame.game.server.entities.Skill;
import com.webgame.game.server.factory.ISkillFactory;

import static com.webgame.game.server.utils.ServerUtils.calcTime;

public class SkillFactory implements ISkillFactory {
    public SkillFactory() {

    }

    @Override
    public synchronized Skill createSkill(SkillKind skillKind) {
        Skill skill = null;
        float[] animSizes1 = {FrameSizes.ANIMATION.getW(), FrameSizes.ANIMATION.getH()};
        float[] standSizes1 = {FrameSizes.LITTLE_SPHERE.getW(), FrameSizes.LITTLE_SPHERE.getH()};

        float[] animSizes2 = {FrameSizes.BLIZZARD.getW(), FrameSizes.BLIZZARD.getH()};
        float[] standSizes2 = {FrameSizes.BLIZZARD.getW(), FrameSizes.BLIZZARD.getH()};

        float[] animSizes3 = {FrameSizes.BIG_ANIMATION.getW(), FrameSizes.BIG_ANIMATION.getH()};
        float[] standSizes3 = {FrameSizes.BIG_ANIMATION.getW(), FrameSizes.BIG_ANIMATION.getH()};

        switch (skillKind) {
            case FIRE_BALL:
                skill = new SingleClientSkill();
                skill.setDamage(150);
                skill.setSpritePath(Configs.SKILLSHEETS_FOLDER + "/fire_002.png");
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/s001.png");
                skill.setCooldown(calcTime(3, 0));
                skill.setSizes(animSizes1, standSizes1);
                break;

            case BLIZZARD:
                skill = new FallingClientSkill();
                skill.setSpritePath(Configs.SKILLSHEETS_FOLDER + "/skills.png");
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/skills.png");
                skill.setCooldown(calcTime(10, 0));
                skill.setDamage(1);
                skill.setSizes(animSizes2, standSizes2);
                break;

            case MAGIC_DEFENCE:
                skill = new BuffClientSkill();
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/cast_001.png");
                break;

            case ICE_BOLT:
                skill = new SingleClientSkill();
                skill.setSpritePath(Configs.SKILLSHEETS_FOLDER + "/ice_003.png");
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/s7.png");
                skill.setDamage(100);
                skill.setSizes(animSizes1, standSizes1);
                break;

            case LIGHTNING:
                skill = new StaticClientSkill();
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/lightning.png");
                skill.setDamage(50);
                skill.setSizes(animSizes1, standSizes1);
                break;

            case TORNADO:
                skill = new StaticClientSkill();
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/skills.png");
                skill.setDamage(50);
                skill.setSizes(animSizes3, standSizes3);
                break;
        }

        if (skill != null)
            skill.setSkillKind(skillKind);
        return skill;
    }
}
