package com.webgame.common.client.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.common.client.animation.GameAnimation;
import com.webgame.common.client.entities.skill.ClientSkill;
import com.webgame.common.client.utils.GameUtils;
import com.webgame.common.server.dto.SpriteAttributesDTO;
import com.webgame.common.server.entities.Player;

import java.util.HashMap;
import java.util.Map;

public class SkillInitiator {
    private Map<String, Texture> cachedTextures;

    public SkillInitiator() {
        cachedTextures = new HashMap<>();
    }

    public void initSkill(ClientSkill skill, Player player) {
        try {
            skill.init(player);

            final String spritePath = skill.getSpritePath();
            final String animSpritePath = skill.getAnimSpritePath();

            Texture standSkillTexture = null;
            Texture animSkillTexture = null;
            TextureRegion standRegion = null;
            SpriteAttributesDTO attributes = skill.getSpriteAttributes();

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

            if (standSkillTexture != null) {
                int x = attributes.getStandTextureRegionX() != null ? attributes.getStandTextureRegionX() : 0;
                int y = attributes.getStandTextureRegionY() != null ? attributes.getStandTextureRegionY() : 0;
                int w = attributes.getStandTextureRegionWidth() != null ? attributes.getStandTextureRegionWidth() : standSkillTexture.getWidth();
                int h = attributes.getStandTextureRegionHeight() != null ? attributes.getStandTextureRegionHeight() : standSkillTexture.getHeight();
                standRegion = new TextureRegion(standSkillTexture, x, y, w, h);
            }

            GameAnimation animation = new GameAnimation(animSkillTexture, attributes.getAnimationDuration(), attributes.getWidth(), attributes.getHeight()
                    , attributes.getxIterations(), attributes.getyIterations(), attributes.getxOffset(), attributes.getyOffset());

            skill.initAnimations(standRegion, animation, attributes.getNumFrames(), attributes.getAnimationState(), attributes.isLooping());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
