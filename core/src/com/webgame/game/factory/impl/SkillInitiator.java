package com.webgame.game.factory.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.Configs;
import com.webgame.game.animation.impl.FlameAnimation;
import com.webgame.game.entities.skill.ClientSkill;
import com.webgame.game.enums.SkillAnimationState;
import com.webgame.game.enums.SkillKind;
import com.webgame.game.factory.ISkillInitiator;
import com.webgame.game.utils.GameUtils;

public class SkillInitiator implements ISkillInitiator {
    public SkillInitiator() {

    }

    @Override
    public void initSkill(ClientSkill skill, SkillKind skillKind) {
        switch (skillKind) {
            case FIRE_BALL:
                Texture standSkillTexture = GameUtils.loadSprite(skill.getSpritePath());
                Texture animSkillTexture = GameUtils.loadSprite(skill.getAnimSpritePath());
                TextureRegion standTexture = new TextureRegion(standSkillTexture, 0, 0, standSkillTexture.getWidth(), standSkillTexture.getHeight());
                skill.initAnimations(standTexture, new FlameAnimation(animSkillTexture), 1, SkillAnimationState.FULL_ANIMATION, false);
                break;
        }
    }
}
