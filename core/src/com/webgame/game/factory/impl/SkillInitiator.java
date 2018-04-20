package com.webgame.game.factory.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.Configs;
import com.webgame.game.animation.impl.*;
import com.webgame.game.entities.skill.ClientSkill;
import com.webgame.game.enums.SkillAnimationState;
import com.webgame.game.enums.SkillKind;
import com.webgame.game.factory.ISkillInitiator;
import com.webgame.game.utils.GameUtils;

import java.util.HashMap;
import java.util.Map;

public class SkillInitiator implements ISkillInitiator {
    private Map<String, Texture> cachedTextures;

    public SkillInitiator() {
        cachedTextures = new HashMap<>();
    }

    @Override
    public synchronized void initSkill(ClientSkill skill, SkillKind skillKind) {
        final String spritePath = skill.getSpritePath();
        final String animSpritePath = skill.getAnimSpritePath();

        Texture standSkillTexture = null;
        Texture animSkillTexture = null;
        TextureRegion standTexture = null;

        if (spritePath != null) {
            if (cachedTextures.containsKey(spritePath))
                standSkillTexture = cachedTextures.get(spritePath);
            else {
                standSkillTexture = GameUtils.loadSprite(spritePath);
                cachedTextures.put(spritePath, standSkillTexture);
            }
        }

        if (animSpritePath != null) {
            if (cachedTextures.containsKey(animSpritePath))
                animSkillTexture = cachedTextures.get(animSpritePath);
            else {
                animSkillTexture = GameUtils.loadSprite(animSpritePath);
                cachedTextures.put(animSpritePath, animSkillTexture);
            }
        }

        switch (skillKind) {
            case FIRE_BALL:
                standTexture = new TextureRegion(standSkillTexture, 0, 0, standSkillTexture.getWidth(), standSkillTexture.getHeight());
                skill.initAnimations(standTexture, new FlameAnimation(animSkillTexture), 1, SkillAnimationState.FULL_ANIMATION, false);
                break;
            case BLIZZARD:
                animSkillTexture = standSkillTexture;
                standTexture = new TextureRegion(standSkillTexture, 5, 245, 30, 30);
                skill.initAnimations(standTexture, new BlizzardFragmentAnimation(animSkillTexture), 30, SkillAnimationState.FULL_ANIMATION, false);
                break;
            case MAGIC_DEFENCE:
                skill.initAnimations(null, new BuffAnimation(animSkillTexture), 1, SkillAnimationState.ANIMATION_ONLY, false);
                break;
            case ICE_BOLT:
                standTexture = new TextureRegion(standSkillTexture, 0, 0, standSkillTexture.getWidth(), standSkillTexture.getHeight());
                skill.initAnimations(standTexture, new IceBlastAnimation(animSkillTexture), 1, SkillAnimationState.FULL_ANIMATION, false);
                break;
            case LIGHTNING:
                skill.initAnimations(null, new LightningAnimation(animSkillTexture), 1, SkillAnimationState.ANIMATION_ONLY, false);
                break;
            case TORNADO:
                skill.initAnimations(null, new TornadoAnimation(animSkillTexture), 1, SkillAnimationState.ANIMATION_ONLY, true);
                break;

            case FIRE_EXPLOSION:
                skill.initAnimations(null, new FireBlastAnimation2(animSkillTexture), 1, SkillAnimationState.ANIMATION_ONLY, false);
                break;
        }
    }
}
