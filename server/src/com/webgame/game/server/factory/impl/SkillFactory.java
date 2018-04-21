package com.webgame.client.server.factory.impl;

import com.webgame.client.Configs;
import com.webgame.client.animation.impl.*;
import com.webgame.client.entities.skill.impl.*;
import com.webgame.client.enums.FrameSizes;
import com.webgame.client.enums.SkillAnimationState;
import com.webgame.client.enums.SkillKind;
import com.webgame.client.server.dto.SpriteAttributesDTO;
import com.webgame.client.server.entities.Skill;
import com.webgame.client.server.factory.ISkillFactory;

import static com.webgame.client.server.utils.ServerUtils.calcTime;

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
                skill.setSpriteAttributes(
                        new SpriteAttributesDTO(0, 0, null, null, FlameAnimation.class.getName(), 1, SkillAnimationState.FULL_ANIMATION, false));
                break;

            case BLIZZARD:
                skill = new FallingClientSkill();
                skill.setSpritePath(Configs.SKILLSHEETS_FOLDER + "/skills.png");
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/skills.png");
                skill.setCooldown(calcTime(10, 0));
                skill.setDamage(1);
                skill.setSizes(animSizes2, standSizes2);
                skill.setSpriteAttributes(new SpriteAttributesDTO(5, 245, 30, 30, BlizzardFragmentAnimation.class.getName(), 30, SkillAnimationState.FULL_ANIMATION, false));
                break;

            case MAGIC_DEFENCE:
                skill = new BuffClientSkill();
                skill.setSpriteAttributes(new SpriteAttributesDTO(null, null, null, null, BuffAnimation.class.getName(), 1, SkillAnimationState.ANIMATION_ONLY, false));
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/cast_001.png");
                break;

            case ICE_BOLT:
                skill = new SingleClientSkill();
                skill.setSpritePath(Configs.SKILLSHEETS_FOLDER + "/ice_003.png");
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/s7.png");
                skill.setDamage(100);
                skill.setSpriteAttributes(new SpriteAttributesDTO(0, 0, null, null, IceBlastAnimation.class.getName(), 1, SkillAnimationState.FULL_ANIMATION, false));
                skill.setSizes(animSizes1, standSizes1);
                break;

            case LIGHTNING:
                skill = new StaticClientSkill();
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/lightning.png");
                skill.setDamage(50);
                skill.setSpriteAttributes(new SpriteAttributesDTO(null, null, null, null, LightningAnimation.class.getName(), 1, SkillAnimationState.ANIMATION_ONLY, false));
                skill.setSizes(animSizes1, standSizes1);
                break;

            case TORNADO:
                skill = new AOEClientSkill();
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/skills.png");
                skill.setDamage(1);
                skill.setSpriteAttributes(new SpriteAttributesDTO(null, null, null, null, TornadoAnimation.class.getName(), 1, SkillAnimationState.ANIMATION_ONLY, true));
                skill.setSizes(animSizes3, standSizes3);
                break;

            case FIRE_EXPLOSION:
                skill = new AOEClientSkill();
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/explosion2.png");
                skill.setDamage(150);
                skill.setSpriteAttributes(new SpriteAttributesDTO(null, null, null, null, FireBlastAnimation2.class.getName(), 1, SkillAnimationState.ANIMATION_ONLY, false));
                skill.setSizes(animSizes3, standSizes3);
                break;
        }

        if (skill != null)
            skill.setSkillKind(skillKind);
        return skill;
    }
}
