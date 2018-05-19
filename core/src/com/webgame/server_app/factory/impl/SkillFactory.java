package com.webgame.server_app.factory.impl;

import com.webgame.common.client.Configs;
import com.webgame.common.client.animation.impl.*;
import com.webgame.common.client.entities.skill.impl.*;
import com.webgame.common.client.enums.FrameSizes;
import com.webgame.common.client.enums.SkillAnimationState;
import com.webgame.common.client.enums.SkillKind;
import com.webgame.common.server.entities.Skill;
import com.webgame.server_app.factory.ISkillFactory;
import com.webgame.common.server.dto.SpriteAttributesDTO;

import static com.webgame.server_app.utils.ServerUtils.calcTime;

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
                        new SpriteAttributesDTO(0, 0, null, null, 1, SkillAnimationState.FULL_ANIMATION,
                                false, 0.05f, 192, 190, 5, 4, 0, 0));
                break;

            case BLIZZARD:
                skill = new FallingClientSkill();
                skill.setSpritePath(Configs.SKILLSHEETS_FOLDER + "/skills.png");
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/skills.png");
                skill.setCooldown(calcTime(10, 0));
                skill.setDamage(1);
                skill.setSizes(animSizes2, standSizes2);
                skill.setSpriteAttributes(new SpriteAttributesDTO(5, 245, 30, 30, 30, SkillAnimationState.FULL_ANIMATION,
                        false, 0.05f, 30, 30, 4, 1, 5, 245));
                break;

            case HEAL:
                break;
            case MAGIC_DEFENCE:
                skill = new BuffClientSkill();
                skill.setSpriteAttributes(new SpriteAttributesDTO(null, null, null, null, 1, SkillAnimationState.ANIMATION_ONLY,
                        false, 0.01f, 192, 190, 5, 3, 0, 0));
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/cast_001.png");
                break;

            case ICE_BOLT:
                skill = new SingleClientSkill();
                skill.setSpritePath(Configs.SKILLSHEETS_FOLDER + "/ice_003.png");
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/s7.png");
                skill.setDamage(100);
                skill.setSpriteAttributes(new SpriteAttributesDTO(0, 0, null, null, 1, SkillAnimationState.FULL_ANIMATION,
                        false, 0.05f, 255, 130, 3, 4, 0, 0));
                skill.setSizes(animSizes1, standSizes1);
                break;

            case LIGHTNING:
                skill = new StaticClientSkill();
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/lightning.png");
                skill.setDamage(50);
                skill.setSpriteAttributes(new SpriteAttributesDTO(null, null, null, null, 1, SkillAnimationState.ANIMATION_ONLY,
                        false, 0.05f, 700, 700, 2, 2, 0, 0));
                skill.setSizes(animSizes1, standSizes1);
                break;

            case TORNADO:
                skill = new AOEClientSkill();
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/skills.png");
                skill.setDamage(1);
                skill.setSpriteAttributes(new SpriteAttributesDTO(null, null, null, null, 1, SkillAnimationState.ANIMATION_ONLY,
                        true, 0.1f, 64, 60, 4, 1, 594, 295));
                skill.setSizes(animSizes3, standSizes3);
                break;

            case FIRE_EXPLOSION:
                skill = new AOEClientSkill();
                skill.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/explosion2.png");
                skill.setDamage(150);
                skill.setSpriteAttributes(new SpriteAttributesDTO(null, null, null, null, 1, SkillAnimationState.ANIMATION_ONLY,
                        false, 0.05f, 255, 130, 3, 4, 0, 0));
                skill.setSizes(animSizes3, standSizes3);
                break;
        }

        skill.setSkillKind(skillKind);
        return skill;
    }
}
